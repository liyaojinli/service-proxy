/* Copyright 2009 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package com.predic8.membrane.core.config;

import static junit.framework.Assert.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.xpath.*;

import org.junit.*;
import org.xml.sax.InputSource;

import com.predic8.membrane.core.Router;

public class CustomRulesConfigurationTest {

	private Router router;
	XPath xpath = XPathFactory.newInstance().newXPath();
	
	@Before
	public void setUp() throws Exception {
		router = Router.init("resources/default-custom-beans.xml");
		router.getConfigurationManager().loadConfiguration("resources/custom-rules.xml");		
	}

	@Test
	public void testRulesConfig() throws Exception {
		StringWriter w = new StringWriter();
		router.getConfigurationManager().getProxies().write(XMLOutputFactory.newInstance().createXMLStreamWriter(w));
		System.out.println(w);
		assertAttribute(w.toString(), "/proxies/serviceProxy/@name", "Service Proxy");
		assertAttribute(w.toString(), "/proxies/serviceProxy/@port", "2001");
		
		assertAttribute(w.toString(), "/proxies/serviceProxy/target/@port", "80");
		assertAttribute(w.toString(), "/proxies/serviceProxy/target/@host", "www.thomas-bayer.com");

		assertAttribute(w.toString(), "/proxies/serviceProxy/transform/@xslt", "strip.xslt");
		
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/counter/@name", "Node 1");
		
		assertElement(w.toString(), "/proxies/serviceProxy/request/adminConsole");
		
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/webServer/@docBase", "docBase");

		assertAttribute(w.toString(), "/proxies/serviceProxy/request/clusterNotification/@validateSignature", "true");
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/clusterNotification/@keyHex", "6f488a642b740fb70c5250987a284dc0");
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/clusterNotification/@timeout", "5000");

		assertAttribute(w.toString(), "/proxies/serviceProxy/request/basicAuthentication/user/@name", "admin");
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/basicAuthentication/user/@password", "adminadmin");

		assertAttribute(w.toString(), "/proxies/serviceProxy/request/regExUrlRewriter/mapping/@regex", "/home");
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/regExUrlRewriter/mapping/@uri", "/index");
		
		assertAttribute(w.toString(), "/proxies/serviceProxy/soapValidator/@wsdl", "http://www.predic8.com:8080/material/ArticleService?wsdl");

		assertAttribute(w.toString(), "/proxies/serviceProxy/rest2Soap/mapping/@regex", "/bank/.*");
		assertAttribute(w.toString(), "/proxies/serviceProxy/rest2Soap/mapping/@soapAction", "");
		assertAttribute(w.toString(), "/proxies/serviceProxy/rest2Soap/mapping/@soapURI", "/axis2/services/BLZService");
		assertAttribute(w.toString(), "/proxies/serviceProxy/rest2Soap/mapping/@requestXSLT", "request.xsl");
		assertAttribute(w.toString(), "/proxies/serviceProxy/rest2Soap/mapping/@responseXSLT", "response.xsl");
		
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/xmlSessionIdExtractor/@namespace", "http://chat.predic8.com/");
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/xmlSessionIdExtractor/@localName", "session");
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/nodes/node/@host", "localhost");
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/nodes/node/@port", "8080");
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/byThreadStrategy/@maxNumberOfThreadsPerEndpoint", "10");
		assertAttribute(w.toString(), "/proxies/serviceProxy/balancer/byThreadStrategy/@retryTimeOnBusy", "1000");		

		assertElement(w.toString(), "/proxies/serviceProxy/balancer/jSessionIdExtractor");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/interceptor/@id", "counter");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/interceptor/@name", "Counter 2");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/response/regExReplacer/@regex", "Hallo");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/response/regExReplacer/@replace", "Hello");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/request/switch/case/@xPath", "//convert");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/request/switch/case/@url", "http://www.thomas-bayer.com/axis2/");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsCSV/@file", "c:/temp/stat.csv");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/response/wsdlRewriter/@registryWSDLRegisterURL", "http://predic8.de/register");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/response/wsdlRewriter/@protocol", "http");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/response/wsdlRewriter/@port", "4000");		
		assertAttribute(w.toString(), "/proxies/serviceProxy/response/wsdlRewriter/@host", "localhost");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/request/accessControl/@file", "resources/acl/acl.xml");		

		assertAttribute(w.toString(), "/proxies/serviceProxy/exchangeStore/@name", "forgetfulExchangeStore");		
		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/@postMethodOnly", "false");		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/@soapOnly", "true");		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/dataSource/@driverClassName", "com.mysql.jdbc.Driver");		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/dataSource/@url", "jdbc:mysql://localhost/proxies");		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/dataSource/@user", "root");		
//		assertAttribute(w.toString(), "/proxies/serviceProxy/statisticsJDBC/dataSource/@password", "rootroot");		

		assertAttribute(w.toString(), "/proxies/proxy/@name", "HTTP Proxy");
		assertAttribute(w.toString(), "/proxies/proxy/@port", "3128");
		
		assertAttribute(w.toString(), "/proxies/proxy/transform/@xslt", "strip.xslt");
		
		assertAttribute(w.toString(), "/proxies/proxy/switch/case/@xPath", "//convert");		
		assertAttribute(w.toString(), "/proxies/proxy/switch/case/@url", "http://www.thomas-bayer.com/axis2/");		
	}
	
	@After
	public void tearDown() throws Exception {
		router.getTransport().closeAll();
	}

	private void assertAttribute(String xml, String xpathExpr, String expected) throws XPathExpressionException {
		assertEquals(expected, xpath.evaluate(xpathExpr, new InputSource(new StringReader(xml))));
	}
	
	private void assertElement(String xml, String xpathExpr) throws XPathExpressionException {
		assertNotNull(xpath.evaluate(xpathExpr, new InputSource(new StringReader(xml)),XPathConstants.NODE));
	}


}
