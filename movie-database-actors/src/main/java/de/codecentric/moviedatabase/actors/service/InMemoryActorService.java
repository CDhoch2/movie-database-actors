package de.codecentric.moviedatabase.actors.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.actors.domain.Actor;
import de.codecentric.moviedatabase.actors.exception.ResourceNotFoundException;

public class InMemoryActorService implements ActorService {

	private Map<UUID, Actor> idToActorMap = new ConcurrentHashMap<UUID, Actor>();

	public InMemoryActorService() {
		// let the dummy Actor have always the same ID to test it easily via
		// command line tools / unit tests
		Actor actor = new Actor(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aeb"), "Harrison", "Ford", new Date(),
				"Ewiger Nebendarsteller");
		actor.getMovieIds().add(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aeb"));
		idToActorMap.put(actor.getId(), actor);

		// Batman vs. Superman
		actor = new Actor(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aef"), "Ben", "Affleck", new Date(),
				"Tütenmann");
		actor.getMovieIds().add(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aef"));
		idToActorMap.put(actor.getId(), actor);

		// The Martian
		actor = new Actor(UUID.fromString("69e550ba-ac8e-4620-bf1b-0792d3854938"), "Matt", "Damon",
				new GregorianCalendar(1970, 10, 8).getTime(), "");
		actor.getMovieIds().add(UUID.fromString("69e550ba-ac8e-4620-bf1b-0792d3854938"));
		idToActorMap.put(actor.getId(), actor);

		actor = new Actor(UUID.fromString("69e550ba-ac8e-4620-bf1b-0792d3854938"), "Jessica", "Chastain",
				new GregorianCalendar(1977, 3, 24).getTime(), "");
		actor.getMovieIds().add(UUID.fromString("69e550ba-ac8e-4620-bf1b-0792d3854938"));
		idToActorMap.put(actor.getId(), actor);

		actor = new Actor(UUID.fromString("269e550ba-ac8e-4620-bf1b-0792d3854938"), "Kate", "Mara",
				new GregorianCalendar(1983, 2, 27).getTime(), "Ewige Reporterin?");
		actor.getMovieIds().add(UUID.fromString("69e550ba-ac8e-4620-bf1b-0792d3854938"));
		idToActorMap.put(actor.getId(), actor);

		// Silver Linings
		actor = new Actor(UUID.fromString("fbcc657d-eb8e-4af7-8bb7-9665247c8c94"), "Bradley", "Cooper",
				new GregorianCalendar(1975, 1, 5).getTime(), "Remembering last night? No? Where is the groom??");
		actor.getMovieIds().add(UUID.fromString("fbcc657d-eb8e-4af7-8bb7-9665247c8c94"));
		idToActorMap.put(actor.getId(), actor);

		actor = new Actor(UUID.fromString("fbcc657d-eb8e-4af7-8bb7-9665247c8c94"), "Jennifer", "Lawrence",
				new GregorianCalendar(1990, 8, 15).getTime(), "And the oscar goes to...");
		actor.getMovieIds().add(UUID.fromString("fbcc657d-eb8e-4af7-8bb7-9665247c8c94"));
		idToActorMap.put(actor.getId(), actor);
		
		// The Revenant
		actor = new Actor(UUID.fromString("3ca4e76d-a041-4ddf-aeed-4fc360efd31a"), "Tom", "Hardy",
				new GregorianCalendar(1977, 9, 15).getTime(), "Bane??");
		actor.getMovieIds().add(UUID.fromString("3ca4e76d-a041-4ddf-aeed-4fc360efd31a"));
		idToActorMap.put(actor.getId(), actor);
		
		actor = new Actor(UUID.fromString("3ca4e76d-a041-4ddf-aeed-4fc360efd31a"), "Leonardo", "DiCaprio",
				new GregorianCalendar(1974, 11, 11).getTime(), "No Oscar for...");
		actor.getMovieIds().add(UUID.fromString("3ca4e76d-a041-4ddf-aeed-4fc360efd31a"));
		idToActorMap.put(actor.getId(), actor);
		
		// Kingsman
		actor = new Actor(UUID.fromString("c62c680d-a71a-4327-b931-5ad126c936ea"), "Colin", "Firth",
				new GregorianCalendar(1960, 9, 10).getTime(), "Known for The Speech");
		actor.getMovieIds().add(UUID.fromString("c62c680d-a71a-4327-b931-5ad126c936ea"));
		idToActorMap.put(actor.getId(), actor);
	}

	@Override
	public void createActor(Actor actor) {
		Assert.notNull(actor);
		idToActorMap.put(actor.getId(), actor);
	}

	@Override
	public void updateActor(Actor actor) {
		// Tags may not be added or removed by this method
		Assert.notNull(actor);
		idToActorMap.put(actor.getId(), actor);
	}

	@Override
	public void deleteActor(UUID id) {
		Assert.notNull(id);
		idToActorMap.remove(id);
	}

	@Override
	public Actor findActorById(UUID id) {
		Assert.notNull(id);
		Actor actor = idToActorMap.get(id);
		if (actor == null) {
			throw new ResourceNotFoundException("Actor not found.");
		}
		return actor;
	}

	@Override
	public List<Actor> findActorBySearchStringAndMovieIds(Set<String> searchWords, Set<UUID> movieIds) {
		Set<Actor> actorsFoundByMovieId = new HashSet<>();
		List<Actor> searchResult = new ArrayList<>();
		if (movieIds == null || movieIds.isEmpty()) {
			actorsFoundByMovieId = new HashSet<>(idToActorMap.values());
		} else {
			for (UUID movieId : movieIds) {
				for (Actor actor : idToActorMap.values()) {
					if (actor.getMovieIds().contains(movieId)) {
						actorsFoundByMovieId.add(actor);
					}
				}
			}
		}
		searchResult.addAll(actorsFoundByMovieId);
		if (searchWords != null && !searchWords.isEmpty()) {
			for (String searchWord : searchWords) {
				for (Iterator<Actor> it = searchResult.iterator(); it.hasNext();) {
					Actor actor = it.next();
					if (!(actor.getFirstname().toLowerCase().contains(searchWord.toLowerCase()))
							&& !(actor.getLastname().toLowerCase().contains(searchWord.toLowerCase()))
							&& !(actor.getBiography() != null
									&& actor.getBiography().toLowerCase().contains(searchWord.toLowerCase()))) {
						it.remove();
					}
				}
			}
		}
		return searchResult;
	}

}
