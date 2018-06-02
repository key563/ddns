package com.aliyuncs.regions;

import com.aliyuncs.auth.Credential;
import com.aliyuncs.exceptions.ClientException;
import org.json.JSONObject;

import java.util.*;

@SuppressWarnings("deprecation")
public class LocationServiceEndpointResolver implements EndpointResolver {

    private DescribeEndpointService describeEndpointService;

    private Map<String, String> serviceCodeMap = new HashMap<String, String>();

    public LocationServiceEndpointResolver() {
        JSONObject endpointData = new JSONObject(EndpointConfig.ENDPOINT_PROFILE);
        for (Object productDataObj : endpointData.getJSONArray("products")) {
            JSONObject productData = (JSONObject)productDataObj;
            String popCode = productData.getString("code");
            String serviceCode = productData.getString("location_service_code");
            if (popCode != null && popCode.length() > 0 && serviceCode != null && serviceCode.length() > 0) {
                serviceCodeMap.put(popCode.toLowerCase(), serviceCode);
            }
        }
    }

    public void setDescribeEndpointService(DescribeEndpointService describeEndpointService) {
        this.describeEndpointService = describeEndpointService;
    }

    public static LocationServiceEndpointResolver initRemoteEndpointsParser() {
        LocationServiceEndpointResolver parser = new LocationServiceEndpointResolver();
        parser.setDescribeEndpointService(new DescribeEndpointServiceImpl());
        return parser;
    }

    @Override
    public Endpoint getEndpoint(String regionId, String product) throws ClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Endpoint getEndpoint(String regionId, String product, String serviceCode, String endpointType,
                                Credential credential, LocationConfig locationConfig) throws ClientException {
        if (serviceCode == null) {
            serviceCode = serviceCodeMap.get(product.toLowerCase());
            if (serviceCode == null) {
                return null;
            }
        }
        Endpoint endpoint = null;

        DescribeEndpointResponse response = describeEndpointService.describeEndpoint(regionId, serviceCode,
            endpointType, credential, locationConfig);
        if (response == null) {
            return endpoint;
        }
        Set<String> regionIds = new HashSet<String>();
        regionIds.add(regionId);

        List<ProductDomain> productDomainList = new ArrayList<ProductDomain>();
        productDomainList.add(new ProductDomain(product, response.getEndpoint()));

        endpoint = new Endpoint(response.getRegionId(), regionIds, productDomainList);
        return endpoint;
    }

}
