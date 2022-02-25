package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

	@Mock
	TournamentRepository tournamentRepository;

	@InjectMocks
	TournamentService tournamentService;

	@Test
	public void find_all_should_return_all_tournaments() {
		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(
				new Tournament(1L, "test1", 8, LocalDate.parse("2022-10-12"),
						LocalDate.parse("2022-10-20"), "", new LinkedList<>(), new LinkedList<>(), new HashSet<>()),
				new Tournament(2L, "test2", 8, LocalDate.parse("2022-10-20"),
						LocalDate.parse("2022-10-25"), "", new LinkedList<>(), new LinkedList<>(), new HashSet<>()),
				new Tournament(3L, "test3", 8, LocalDate.parse("2022-10-25"),
						LocalDate.parse("2022-10-30"), "", new LinkedList<>(), new LinkedList<>(), new HashSet<>())
		));

		List<Tournament> tournaments = tournamentService.getAllRaw();

		assertEquals(1L, tournaments.get(0).getTournamentId());
		assertEquals(2L, tournaments.get(1).getTournamentId());
		assertEquals(3L, tournaments.get(2).getTournamentId());

	}

	@Test
	public void get_tournament_by_id_should_return_correct_tournament() {
		when(tournamentRepository.findById(1L)).thenReturn(
				Optional.of(new Tournament(1L, "test1", 8, LocalDate.parse("2022-10-12"), LocalDate.parse("2022-10-20"), "", new LinkedList<>(), new LinkedList<>(), new HashSet<>())
				));

		Tournament tournament = tournamentService.getTournamentRaw(1L);
		assertEquals(1L, tournament.getTournamentId());
		assertEquals("test1", tournament.getTitle());
	}

	@Test
	public void get_tournament_by_id_should_throw_exception() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			tournamentService.getTournamentRaw(1L);
		});

		String expectedMessage = "Tournament not found.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}