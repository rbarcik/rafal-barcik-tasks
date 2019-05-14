package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));
        trelloLists.add(new TrelloListDto());

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));
        trelloBoards.add(new TrelloBoardDto());

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());

    }
    @Test
    public void shoulFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }
    @Test
    public void shouldCreateCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Trello CardDto", "Trello CardDto description", "Trello CardDto pos", "1");
        TrelloCard mappedTrelloCard = new TrelloCard("Trello CardDto", "Trello CardDto description", "Trello CardDto pos", "1");
        TrelloDto trelloDto = new TrelloDto(12345, 67890);
        AttachmentByTypeDto attachmentByTypeDto = new AttachmentByTypeDto(trelloDto);
        BadgesDto badgesDto = new BadgesDto(15, attachmentByTypeDto);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Trello CardDto", "http://test.com", badgesDto);
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(mappedTrelloCard);
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createdTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto testedCreatedTrelloCardDto = trelloFacade.createCard(trelloCardDto);
        //Then
        assertEquals(12345, testedCreatedTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(67890, testedCreatedTrelloCardDto.getBadges().getAttachmentsByType().getTrello().getCard());
        assertEquals(trelloDto, testedCreatedTrelloCardDto.getBadges().getAttachmentsByType().getTrello());
        assertEquals(attachmentByTypeDto, testedCreatedTrelloCardDto.getBadges().getAttachmentsByType());
        assertEquals(15, testedCreatedTrelloCardDto.getBadges().getVotes());
        assertEquals(badgesDto, testedCreatedTrelloCardDto.getBadges());
        assertEquals("1", testedCreatedTrelloCardDto.getId());
        assertEquals("Trello CardDto", testedCreatedTrelloCardDto.getName());
        assertEquals("http://test.com", testedCreatedTrelloCardDto.getShortUrl());
    }
    @Test
    public void shouldCreateEmptyCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        TrelloCard mappedTrelloCard = new TrelloCard("Trello CardDto", "Trello CardDto description", "Trello CardDto pos", "1");
        TrelloDto trelloDto = new TrelloDto();
        AttachmentByTypeDto attachmentByTypeDto = new AttachmentByTypeDto();
        BadgesDto badgesDto = new BadgesDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(mappedTrelloCard);
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createdTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto testedCreatedTrelloCardDto = trelloFacade.createCard(trelloCardDto);
        //Then
        assertNull(testedCreatedTrelloCardDto.getBadges());
        assertNull(testedCreatedTrelloCardDto.getId());
        assertNull(testedCreatedTrelloCardDto.getName());
        assertNull(testedCreatedTrelloCardDto.getShortUrl());
    }
}
