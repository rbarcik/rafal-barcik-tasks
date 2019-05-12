package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "TrelloListDto1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "TrelloListDto1", false);
        List<TrelloListDto> listTrelloListDto = new ArrayList<>();
        listTrelloListDto.add(trelloListDto1);
        listTrelloListDto.add(trelloListDto2);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "TrelloBoardDto1", listTrelloListDto );
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "TrelloBoardDto2", listTrelloListDto);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(2, trelloBoards.size());
        assertEquals("TrelloBoardDto1", trelloBoards.get(0).getName());
        assertEquals("TrelloBoardDto2", trelloBoards.get(1).getName());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "TrelloList1", false);
        TrelloList trelloList2 = new TrelloList("2", "TrelloList1", false);
        List<TrelloList> listTrelloList = new ArrayList<>();
        listTrelloList.add(trelloList1);
        listTrelloList.add(trelloList2);
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "TrelloBoard1", listTrelloList );
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "TrelloBoard2", listTrelloList);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(2, trelloBoardDtos.size());
        assertEquals("TrelloBoard1", trelloBoardDtos.get(0).getName());
        assertEquals("TrelloBoard2", trelloBoardDtos.get(1).getName());
    }

    @Test
    public void mapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "TrelloListDto1", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "TrelloListDto1", false);
        List<TrelloListDto> listTrelloListDto = new ArrayList<>();
        listTrelloListDto.add(trelloListDto1);
        listTrelloListDto.add(trelloListDto2);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(listTrelloListDto);
        //Then
        assertEquals(2, trelloLists.size());
        assertEquals("TrelloListDto1", trelloLists.get(0).getName());
        assertEquals("TrelloListDto1", trelloLists.get(1).getName());
    }

    @Test
    public void mapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "TrelloList1", false);
        TrelloList trelloList2 = new TrelloList("2", "TrelloList1", false);
        List<TrelloList> listTrelloList = new ArrayList<>();
        listTrelloList.add(trelloList1);
        listTrelloList.add(trelloList2);
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(listTrelloList);
        //Then
        assertEquals(2, trelloListDtos.size());
        assertEquals("TrelloList1", trelloListDtos.get(0).getName());
        assertEquals("TrelloList1", trelloListDtos.get(1).getName());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard1 = new TrelloCard("trelloCard1", "trelloCard1 description", "trelloCard1 pos", "1");
        TrelloCard trelloCard2 = new TrelloCard("trelloCard2", "trelloCard2 description", "trelloCard2 pos", "2");
        //When
        TrelloCardDto trelloCardDto1 = trelloMapper.mapToCardDto(trelloCard1);
        TrelloCardDto trelloCardDto2 = trelloMapper.mapToCardDto(trelloCard2);
        //Then
        assertEquals("trelloCard1", trelloCardDto1.getName());
        assertEquals("trelloCard1 description", trelloCardDto1.getDescription());
        assertEquals("trelloCard2", trelloCardDto2.getName());
        assertEquals("trelloCard2 description", trelloCardDto2.getDescription());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto1 = new TrelloCardDto("trelloCardDto1", "trelloCardDto1 description", "trelloCardDto1 pos", "1");
        TrelloCardDto trelloCardDto2 = new TrelloCardDto("trelloCardDto2", "trelloCardDto2 description", "trelloCardDto2 pos", "2");
        //When
        TrelloCard trelloCard1 = trelloMapper.mapToCard(trelloCardDto1);
        TrelloCard trelloCard2 = trelloMapper.mapToCard(trelloCardDto2);
        //Then
        assertEquals("trelloCardDto1", trelloCard1.getName());
        assertEquals("trelloCardDto1 description", trelloCard1.getDescription());
        assertEquals("trelloCardDto2", trelloCard2.getName());
        assertEquals("trelloCardDto2 description", trelloCard2.getDescription());
    }
}