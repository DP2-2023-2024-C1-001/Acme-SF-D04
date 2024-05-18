
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.userStory.UserStory;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode = false")
	Collection<Project> findAllProjectsByManagerId(int managerId);

	@Query("select us from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	Collection<UserStory> findAllUserStoriesByManagerId(int managerId);

	//UserStory

	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority = acme.entities.userStory.Priority.MUST and us.draftMode = false")
	Integer countOfUserStoryWithPriorityMust(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority = acme.entities.userStory.Priority.SHOULD and us.draftMode = false")
	Integer countOfUserStoryWithPriorityShould(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority = acme.entities.userStory.Priority.COULD and us.draftMode = false")
	Integer countOfUserStoryWithPriorityCould(int managerId);

	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority = acme.entities.userStory.Priority.WONT and us.draftMode = false")
	Integer countOfUserStoryWithPriorityWont(int managerId);

	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double avgUserStoryEstimatedCost(int managerId);

	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double devUserStoryEstimatedCost(int managerId);

	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double minUserStoryEstimatedCost(int managerId);

	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId and us.draftMode = false")
	double maxUserStoryEstimatedCost(int managerId);

	//Projects

	@Query("select p.cost.currency, avg(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false GROUP BY p.cost.currency")
	Collection<Object[]> avgProjectCost(int managerId);

	@Query("select p.cost.currency, stddev(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false GROUP BY p.cost.currency")
	Collection<Object[]> devProjectCost(int managerId);

	@Query("select p.cost.currency, min(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false GROUP BY p.cost.currency")
	Collection<Object[]> minProjectCost(int managerId);

	@Query("select p.cost.currency, max(p.cost.amount) from Project p where p.manager.id = :managerId and p.draftMode = false GROUP BY p.cost.currency")
	Collection<Object[]> maxProjectCost(int managerId);

	@Query("select sc.systemCurrency from SystemConfiguration sc")
	String getActualSystemConfiguration();

	default Map<String, Double> convertToMap(final Collection<Object[]> objectSet) {
		Map<String, Double> res = new HashMap<>();
		for (Object[] keyValuePair : objectSet) {
			String currency = (String) keyValuePair[0];
			Double statistic = (Double) keyValuePair[1];
			res.put(currency, statistic);
		}
		return res;
	}

}
