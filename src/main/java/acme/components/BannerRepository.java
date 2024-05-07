
package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.displayPeriodInitial <= :actualMoment AND b.displayPeriodFinal>= :actualMoment")
	int countBanners(Date actualMoment);

	@Query("select b from Banner b where b.displayPeriodInitial <= :actualMoment AND b.displayPeriodFinal>= :actualMoment")
	List<Banner> findManyBanners(PageRequest pageRequest, Date actualMoment);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		PageRequest page;
		List<Banner> list;
		Date actualMoment;

		actualMoment = MomentHelper.getCurrentMoment();

		count = this.countBanners(actualMoment);
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findManyBanners(page, actualMoment);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
