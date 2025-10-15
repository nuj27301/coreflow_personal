package com.coreflow.shop.admin.statistics;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StatisticsMapper {
	
	List<Map<String, Object>> getDailyStaistics(String date);
	
	List<Map<String, Object>> getHourlyStatistics(@Param("start_date") String start_date, @Param("end_date") String end_date);
	
	List<Map<String, Object>> getWeeklyStatistics(@Param("start_date") String start_date, @Param("end_date") String end_date);
	
	List<Map<String, Object>> getMonthlyStatistics(String year);
	
	int gettodayOrderCount();
	
	int gettodaySalesTotal();
	
	int getrefundCount();
	
	int getnewMemberCount();
	
	int getMonthlyCount();
	
	int getMonthlySalesTotal();
	
	int getTotalProductCount();
	
	int getSoldOutProductCount();
	
	List<Map<String, Object>> getMonthlyOrderCategoryStats();
	
	List<Map<String, Object>> getThisWeekOrderAmount();
	
	List<Map<String, Object>> getRecentOrders();
}
