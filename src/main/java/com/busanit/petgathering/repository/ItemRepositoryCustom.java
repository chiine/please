package com.busanit.petgathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busanit.petgathering.dto.ItemSearchDto;
import com.busanit.petgathering.dto.MainItemDto;
import com.busanit.petgathering.entity.Item;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getCategoryItemPage(ItemSearchDto itemSearchDto, String itemCategory, Pageable pageable);
}
