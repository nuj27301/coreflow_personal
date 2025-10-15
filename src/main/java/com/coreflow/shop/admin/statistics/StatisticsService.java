package com.coreflow.shop.admin.statistics;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

	private final StatisticsMapper statisticsMapper;
	
	public List<Map<String, Object>> getDailyStaistics(String date) {
		return statisticsMapper.getDailyStaistics(date);
	}
	
	public List<Map<String, Object>> getHourlyStatistics(@Param("start_date") String start_date,  @Param("end_date") String end_date) {
		return statisticsMapper.getHourlyStatistics(start_date, end_date);
	}
	
	public List<Map<String, Object>> getWeeklyStatistics(@Param("start_date") String start_date, @Param("end_date") String end_date) {
		return statisticsMapper.getWeeklyStatistics(start_date, end_date);
	}
	
	public List<Map<String, Object>> getMonthlyStatistics(String year) {
		return statisticsMapper.getMonthlyStatistics(year);
	}
	
	public int gettodayOrderCount() {
		return statisticsMapper.gettodayOrderCount();
	}
	
	public int gettodaySalesTotal() {
		return statisticsMapper.gettodaySalesTotal();
	}
	
	public int getrefundCount() {
		return statisticsMapper.getrefundCount();
	}
	
	public int getnewMemberCount() {
		return statisticsMapper.getnewMemberCount();
	}
	
	public int getMonthlyCount() {
		return statisticsMapper.getMonthlyCount();
	}
	
	public int getMonthlySalesTotal() {
		return statisticsMapper.getMonthlySalesTotal();
	}
	
	public int getTotalProductCount() {
		return statisticsMapper.getTotalProductCount();
	}
	
	public int getSoldOutProductCount() {
		return statisticsMapper.getSoldOutProductCount();
	}
	
	public List<Map<String, Object>> getMonthlyOrderCategoryStats() {
		return statisticsMapper.getMonthlyOrderCategoryStats();
	}
	
	public List<Map<String, Object>> getThisWeekOrderAmount() {
		return statisticsMapper.getThisWeekOrderAmount();
	}
	
	public List<Map<String, Object>> getRecentOrders() {
		return statisticsMapper.getRecentOrders();
	}
}
