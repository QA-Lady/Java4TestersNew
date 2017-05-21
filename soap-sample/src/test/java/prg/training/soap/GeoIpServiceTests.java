package prg.training.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("204.212.127.0");
        System.out.println(geoIP);
        Assert.assertEquals(geoIP.getCountryCode(), "USA"); //not sure why Aruba IP address is identified like USA ?!
    }

    @Test
    public void testInvalidIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("194.28.29.xxx");
        Assert.assertEquals(geoIP.getReturnCodeDetails(), "Invalid IP address");
    }
}
