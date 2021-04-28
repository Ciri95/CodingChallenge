package com.ciri.codingchallenge;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodingChallengeControllerTest {

    @Test
    void testShowresultsSourceNull() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(null);

        CodingChallengeController controller = new CodingChallengeController();
        assertThrows(NullPointerException.class, () -> controller.showresults(mockedRestController, "US"));
    }

    @Test
    void testShowresultsRegionNull() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertNull(controller.showresults(mockedRestController, null));
    }

    @Test
    void testShowresultsRegionALL() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertEquals(matchtestResultDataFormat(testdata), controller.showresults(mockedRestController, "ALL"));
    }

    @Test
    void testShowresultsRegionIsEmpty() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertNull(controller.showresults(mockedRestController, ""));
    }

    @Test
    void testShowresultsRegionIsNotAValidRegion() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertNull(controller.showresults(mockedRestController, "Test"));
    }

    @Test
    void testShowresultsRegionFilterWithResult() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertEquals(testdata.getPrefixes().get(0).toString(), controller.showresults(mockedRestController, "EU"));
    }

    @Test
    void testShowresultsRegionFilterWithNoResult() {

        RestTemplate mockedRestController = Mockito.mock(RestTemplate.class);
        IpRange testdata = createTestdata();
        Mockito.when(mockedRestController.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IpRange.class)).thenReturn(testdata);

        CodingChallengeController controller = new CodingChallengeController();
        assertEquals("", controller.showresults(mockedRestController, "AP"));
    }

    private String matchtestResultDataFormat(IpRange testdata) {
        return testdata.toString().replace("[", "").replace("}, ", "}\n").replace("]", "\n").trim();
    }

    private IpRange createTestdata() {
        IpRange ipRangeResult = new IpRange();
        PrefixValue prefixValue1 = new PrefixValue();
        prefixValue1.setIp_prefix("13.34.43.192/27");
        prefixValue1.setRegion("eu-central-1");
        prefixValue1.setService("AMAZON");
        prefixValue1.setNetwork_border_group("eu-central-1");
        PrefixValue prefixValue2 = new PrefixValue();
        prefixValue2.setIp_prefix("52.93.17.0/24");
        prefixValue2.setRegion("us-east-1");
        prefixValue2.setService("AMAZON");
        prefixValue2.setNetwork_border_group("us-east-1-iah-1");
        Ipv6PrefixValue ipv6PrefixValue1 = new Ipv6PrefixValue();
        ipv6PrefixValue1.setIpv6_prefix("2600:1f70:4000:100::/56");
        ipv6PrefixValue1.setRegion("me-south-1");
        ipv6PrefixValue1.setService("AMAZON");
        ipv6PrefixValue1.setNetwork_border_group("me-south-1");
        Ipv6PrefixValue ipv6PrefixValue2 = new Ipv6PrefixValue();
        ipv6PrefixValue2.setIpv6_prefix("2400:7fc0:4000:400::/56");
        ipv6PrefixValue2.setRegion("cn-north-1");
        ipv6PrefixValue2.setService("AMAZON");
        ipv6PrefixValue2.setNetwork_border_group("cn-north-1");
        ipRangeResult.setPrefixes(List.of(prefixValue1, prefixValue2));
        ipRangeResult.setIpv6_prefixes(List.of(ipv6PrefixValue1, ipv6PrefixValue2));
        return ipRangeResult;
    }
}