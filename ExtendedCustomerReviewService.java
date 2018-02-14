package com.yesu.extend;

import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.model.CustomerReviewModel;


import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import exception.DisallowedReviewException;
import org.springframework.beans.factory.annotation.Required;

public class ExtendedCustomerReviewService {

	/*
	 * Based on the implementation of the languageFilter
	 * autowired into this class, the logic for allowing
	 * and disallowing reviews will vary
	 */
	private LanguageFilter languageFilter;
	private DefaultCustomerReviewService defaultCustomerReviewService;
	
	@Required
	public void setLanguageFilter(LanguageFilter languageFilter) {
		this.languageFilter = languageFilter;
	}
	
	@Required
	public void setDefaultCustomerReviewService(DefaultCustomerReviewService defaultCustomerReviewService) {
		this.defaultCustomerReviewService = defaultCustomerReviewService;
	}
	
	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product)
			throws Exception {
		if( !languageFilter.isLanguageAllowed(headline) 
				|| !languageFilter.isLanguageAllowed(comment) ) {
			throw new DisallowedReviewException("The language used in the review is not allowed.");
		}
		
		if( rating < 0 ) {
			throw new DisallowedReviewException("Rating is less than 0");
		}
		
		return defaultCustomerReviewService.createCustomerReview(rating, headline, comment, user, product);
	}
	
	/*
	 * this method will stream through all reviews for a
	 * product in parallel, filter out those reviews
	 * with a rating higher than the lower bound and lower
	 * than the upper bound and return the count of result
	 * set
	 */
	public long getNumberOfReviewsWithRatingsWithinRange(ProductModel product, double low, double high) {
		return defaultCustomerReviewService
			.getReviewsForProduct(product)
			.parallelStream()
			.filter( s -> s.getRating() >= low)
			.filter( s -> s.getRating() <= high)
			.count();
	}
	
}
