package mag.near.lux.util

import mag.near.lux.BaseSpec

class PropsUtilSpec extends BaseSpec{

    def"get URL for mages registry from properties"(){
        expect:
        PropsUtil propsUtil = new PropsUtil('endPoints.properties')
        propsUtil.getPropertyByNmae('get.mages.url') == 'http://localhost:8080/mages-registry-web/mages/{limit}'
    }

}
