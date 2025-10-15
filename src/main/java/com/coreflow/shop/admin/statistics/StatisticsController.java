package com.coreflow.shop.admin.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/statist/*")
public class StatisticsController {

	private final StatisticsService statisticsService;
	
	// 주문통계
	@GetMapping("/static_sale_all")
	public void static_sale_all() throws Exception {
	}
	
	// 일자별
	@GetMapping("/daily")
	public ResponseEntity<List<Map<String, Object>>> getDailyStatistics(String date) throws Exception {
		return ResponseEntity.ok(statisticsService.getDailyStaistics(date));
	}
	
	// 시간별
	@GetMapping("/hourly")
	public ResponseEntity<List<Map<String, Object>>> getHourlyStatistics(String start_date, String end_date) throws Exception {
		return ResponseEntity.ok(statisticsService.getHourlyStatistics(start_date, end_date));
	}
	
	// 요일별
	@GetMapping("/weekly")
	public ResponseEntity<List<Map<String, Object>>> getWeeklyStatistics(String start_date, String end_date) throws Exception {
		return ResponseEntity.ok(statisticsService.getWeeklyStatistics(start_date, end_date));
	}
	
	// 월별
	@GetMapping("/monthly")
	public ResponseEntity<List<Map<String, Object>>> getMonthlyStatistics(String year) throws Exception {
		return ResponseEntity.ok(statisticsService.getMonthlyStatistics(year));
	}
}
