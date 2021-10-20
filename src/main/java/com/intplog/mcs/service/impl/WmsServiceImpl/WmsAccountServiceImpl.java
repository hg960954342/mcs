package com.intplog.mcs.service.impl.WmsServiceImpl;

import com.intplog.mcs.common.WmsJsonData;
import com.intplog.mcs.service.WmsService.WmsAccountService;
import com.intplog.mcs.service.impl.InitDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/9/8 16:37
 */
@Service
public class WmsAccountServiceImpl implements WmsAccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WmsJsonData emptyBoxRelease(Object object) {
        try {
            String url = "/wcs/bcr";
            return this.restTemplate.postForObject(InitDataListener.WMS_URL + url, object, WmsJsonData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            WmsJsonData wmsJsonData = new WmsJsonData();
            wmsJsonData.setCompression(false);
            wmsJsonData.setMessage(ex.getMessage());
            return wmsJsonData;
        }
    }

    @Override
    public WmsJsonData rfReport(Object object) {
        try {
            String url = "/api/ExternalApi/CallAction";
            return this.restTemplate.postForObject(InitDataListener.WMS_URL + url, object, WmsJsonData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            WmsJsonData wmsJsonData = new WmsJsonData();
            wmsJsonData.setCompression(false);
            wmsJsonData.setMessage(ex.getMessage());
            return wmsJsonData;
        }
    }

    @Override
    public WmsJsonData weighReport(Object object) {
        try {
            String url = "/api/ExternalApi/CallAction";
            return this.restTemplate.postForObject(InitDataListener.WMS_URL + url, object, WmsJsonData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            WmsJsonData wmsJsonData = new WmsJsonData();
            wmsJsonData.setCompression(false);
            wmsJsonData.setMessage(ex.getMessage());
            return wmsJsonData;
        }
    }

    @Override
    public WmsJsonData boxTurnOver(Object object) {
        try {
            String url = "/api/ExternalApi/CallAction";
            return this.restTemplate.postForObject(InitDataListener.WMS_URL + url, object, WmsJsonData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            WmsJsonData wmsJsonData = new WmsJsonData();
            wmsJsonData.setCompression(false);
            wmsJsonData.setMessage(ex.getMessage());
            return wmsJsonData;
        }
    }

    @Override
    public WmsJsonData staticWeight(Object object) {
        try {
            String url = "/api/ExternalApi/CallAction";
            return this.restTemplate.postForObject(InitDataListener.WMS_URL + url, object, WmsJsonData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            WmsJsonData wmsJsonData = new WmsJsonData();
            wmsJsonData.setCompression(false);
            wmsJsonData.setMessage(ex.getMessage());
            return wmsJsonData;
        }
    }
}
