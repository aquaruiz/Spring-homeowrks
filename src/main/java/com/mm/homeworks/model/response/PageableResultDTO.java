package com.mm.homeworks.model.response;

import java.util.List;

public class PageableResultDTO<T> {
	private Long count;
	private List<T> results;
	private int page;
	private int totalPages;

	public PageableResultDTO(Long count, List<T> results, int page, int totalPages) {
		this.count = count;
		this.results = results;
		this.page = page;
		this.totalPages = totalPages;
	}

	public Long getCount() {
		return count;
	}

	public List<T> getResults() {
		return results;
	}

	public int getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}
}
