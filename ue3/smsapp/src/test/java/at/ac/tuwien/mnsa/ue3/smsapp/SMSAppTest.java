package at.ac.tuwien.mnsa.ue3.smsapp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.mnsa.ue3.properties.PropertiesServiceFactory;
import at.ac.tuwien.mnsa.ue3.properties.SMSPropertiesService;

public class SMSAppTest {

	private static final Logger LOG = LoggerFactory.getLogger(SMSAppTest.class);

	private static Properties prop;

	private static SMSApp smsApp;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		LOG.info(" ");
		LOG.info("=======================");
		LOG.info("Setting up UnitTests...");
		LOG.info("=======================");

		smsApp = SMSApp.getInstance();

		prop = PropertiesServiceFactory.getPropertiesService().getProperties();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LOG.info(" ");
		LOG.info("====================");
		LOG.info("Starting teardown...");
		LOG.info("====================");

		smsApp.close();

		LOG.info("Teardown done. Shutting down now...");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sendPduSMS() {
		// TODO whole stuff
	}

	@Test
	public void sendTextSMS() {
		String answer[] = { "", "" };

		LOG.info("\n");
		LOG.info("====================");
		LOG.info("Send SMS in TextMode");
		LOG.info("====================");

		// Is there a valid RECIPIENT_KEY available in the properties file?
		if ((prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)) != null
				&& (prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)
						.length() > 0)) {

			LOG.info("Enabling TextMode...");
			answer = smsApp.sendATCommand("AT+CMGF=1", SMSApp.DELAY_DEFAULT);

			LOG.info("Return-Code: {}", answer[1]);
			LOG.info("Telephone sent: {}", answer[0]);

			LOG.info("Sending SMS to {}",
					prop.getProperty(SMSPropertiesService.RECIPIENT_KEY));
			smsApp.sendATCommand(
					"AT+CMGS=\""
							+ prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)
							+ "\"\r\n", SMSApp.DELAY_DEFAULT);

			answer = smsApp.sendATCommand("these go to 11" + '\032',
					SMSApp.DELAY_SMS);

			LOG.info("Return-Code: {}", answer[1]);
			LOG.info("Telephone sent: {}", answer[0]);

		} else
			LOG.error("No recipient telephone number specified!");

		assertTrue(answer[1].equalsIgnoreCase("ok"));
	}

	@Test
	public void getModemInformation() {
		String answer[];

		LOG.info(" ");
		LOG.info("=================");
		LOG.info("Modem Information");
		LOG.info("=================");

		answer = smsApp.sendATCommand("ATI7", SMSApp.DELAY_DEFAULT);

		LOG.info("Return-Code: {}", answer[1]);
		LOG.info("Telephone sent: {}", answer[0]);

		assertTrue(answer[1].equalsIgnoreCase("ok"));
	}

	@Test
	public void testCall() {
		String answer[] = { "", "" };

		LOG.info(" ");
		LOG.info("==============");
		LOG.info("Telephone-Call");
		LOG.info("==============");

		// Is there a valid RECIPIENT_KEY available in the properties file?
		if ((prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)) != null
				&& (prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)
						.length() > 0)) {
			LOG.info("Sending \"ATD"
					+ prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)
					+ ";\"");

			answer = smsApp
					.sendATCommand(
							"ATD+"
									+ prop.getProperty(SMSPropertiesService.RECIPIENT_KEY)
									+ ";", SMSApp.DELAY_CALL);

			LOG.info("Return-Code: {}", answer[1]);
			LOG.info("Telephone sent: {}", answer[0]);

			// LOG.info("Hangup telephone...");
			// sms.sendATCommand("ATH", SMSApp.DELAY_DEFAULT);

		} else
			LOG.error("No recipient telephone number specified!");

		assertTrue(answer[1].equalsIgnoreCase("ok")
				|| answer[1].equalsIgnoreCase("no carrier"));
	}
}