
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(tm) from TrainingModule tm where tm.developer.id = :developerId AND tm.updateMoment != null")
	Integer totalNumberOfTrainingModulesWithUpdateMoment(int developerId);

	@Query("select count(ts) from TrainingSession ts where ts.trainingModule.developer.id = :developerId AND ts.link is not null and ts.link not like ''")
	Integer totalNumberOfTrainingSessionsWithLink(int developerId);

	@Query("select avg(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId")
	Double averageTimeOfTrainingModules(int developerId);

	@Query("select stddev(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId")
	Double deviationTimeOfTrainigModules(int developerId);

	@Query("select min(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId")
	Integer minimumTimeOfTrainingModules(int developerId);

	@Query("select max(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId")
	Integer maximumTimeOfTrainingModules(int developerId);

}
