
package acme.features.manager.userstoryproject;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.UserStoryProject;
import acme.entities.userStory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryProjectRepository extends AbstractRepository {

	@Query("select up from UserStoryProject up where up.id = :id")
	UserStoryProject findOneUserStoryProjectById(int id);

	@Query("select up from UserStoryProject up where up.project.manager.id = :id")
	Collection<UserStoryProject> findUserStoryProjectByManagerId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select us from UserStory us where us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query(" select us from UserStory us where us.draftMode = false")
	Collection<UserStory> findAllPublishedUserStories();

	@Query(" select up from UserStoryProject up where up.project.id = :pid and up.userStory.id = :usid")
	UserStoryProject findOneAssignationByProjectIdAndUserStoryId(int pid, int usid);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode = true")
	Collection<Project> findDraftModeProjectsByManagerId(int managerId);

}
