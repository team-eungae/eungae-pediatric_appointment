package com.playdata.eungae.admin.service;

import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.playdata.eungae.hospital.domain.Hospital;
import com.playdata.eungae.hospital.domain.HospitalSchedule;
import com.playdata.eungae.hospital.repository.HospitalRepository;
import com.playdata.eungae.hospital.repository.HospitalScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminService {

	private final HospitalRepository hospitalRepository;
	private final HospitalScheduleRepository hospitalScheduleRepository;

	@Value("${api.open-data.key}")
	String apiKey;

	@Value("${api.open-data.url}")
	String url;

	public void savePublicHospitalData() {
		for (int i = 1; i <= 1; i++) { //원래는 761
			try {
				String finalUrl = url + "?serviceKey=" + apiKey + "&pageNo=" + i + "&numOfRows=100";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(finalUrl);

				doc.getDocumentElement().normalize();

				NodeList nodeList = doc.getElementsByTagName("item");

				for (int itemIndex = 0; itemIndex < nodeList.getLength(); itemIndex++) {

					Node node = nodeList.item(itemIndex);

					Element element = (Element)node;

					if (getTagValue("dgidIdName", element).contains("소아")) {
						String monOpen = getTagValue("dutyTime1s", element);
						String monClose = getTagValue("dutyTime1c", element);
						String tueOpen = getTagValue("dutyTime2s", element);
						String tueClose = getTagValue("dutyTime2c", element);
						String wedOpen = getTagValue("dutyTime3s", element);
						String wedClose = getTagValue("dutyTime3c", element);
						String thuOpen = getTagValue("dutyTime4s", element);
						String thuClose = getTagValue("dutyTime4c", element);
						String friOpen = getTagValue("dutyTime5s", element);
						String friClose = getTagValue("dutyTime5c", element);
						String satOpen = getTagValue("dutyTime5s", element);
						String satClose = getTagValue("dutyTime5c", element);
						String sunOpen = getTagValue("dutyTime2s", element); // 일요일은 공공데이터에서 제공하지 않음 그래서 토요일 것으로 대체
						String sunClose = getTagValue("dutyTime2c", element);

						HospitalSchedule hospitalSchedule = HospitalSchedule.builder()
							.lunchHour("1200")
							.lunchEndHour("1300")
							.monOpen(monOpen == null ? "0900" : monOpen)
							.monClose(monClose == null ? "1800" : monClose)
							.tueOpen(tueOpen == null ? "0900" : tueOpen)
							.tueClose(tueClose == null ? "1800" : tueClose)
							.wedOpen(wedOpen == null ? "0900" : wedOpen)
							.wedClose(wedClose == null ? "1800" : wedClose)
							.thuOpen(thuOpen == null ? "0900" : thuOpen)
							.thuClose(thuClose == null ? "1800" : thuClose)
							.friOpen(friOpen == null ? "0900" : friOpen)
							.friClose(friClose == null ? "1800" : friClose)
							.satOpen(satOpen == null ? "0900" : satOpen)
							.satClose(satClose == null ? "1800" : satClose)
							.sunOpen(sunOpen == null ? "0900" : sunOpen)
							.sunClose(sunClose == null ? "1800" : sunClose)
							.build();

						hospitalScheduleRepository.save(hospitalSchedule);

						Hospital hospitalBuilder = Hospital.builder()
							.hospitalId("test1")
							.password("1234")
							.name(getTagValue("dutyName", element))
							.deposit(1000)
							.xCoordinate(Double.parseDouble(getTagValue("wgs84Lon", element)))
							.yCoordinate(Double.parseDouble(getTagValue("wgs84Lat", element)))
							.address(getTagValue("dutyAddr", element))
							.contact(getTagValue("dutyTel1", element))
							.hospitalSchedule(hospitalSchedule)
							.build();

						hospitalRepository.save(hospitalBuilder);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getTagValue(String tag, Element element) {
		try {
			NodeList nList = element.getElementsByTagName(tag)
				.item(0)
				.getChildNodes();
			return nList.item(0).getTextContent();
		} catch (NullPointerException e) {
			log.error("{} 가 존재하지 않아 null로 반환합니다.", tag);
			return null;
		}
	}
}
