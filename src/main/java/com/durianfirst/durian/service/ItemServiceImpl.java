package com.durianfirst.durian.service;


import com.durianfirst.durian.dto.ItemDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Item;
import com.durianfirst.durian.entity.ItemImage;
import com.durianfirst.durian.repository.ItemImageRepository;
import com.durianfirst.durian.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;


    @Transactional
    @Override
    public Long register(ItemDTO itemDTO) {

        Map<String, Object> entityMap = dtoToEntity(itemDTO);
        Item item = (Item) entityMap.get("item");
        List<ItemImage> itemImageList =(List<ItemImage>) entityMap.get("imgList");

        itemRepository.save(item);
//        itemImageList.forEach(itemImage -> {
//            itemImageRepository.save(itemImage);
//        });
        if (itemImageList != null) {
            itemImageList.forEach(itemImage -> {
                itemImageRepository.save(itemImage);
            });
        }

        return item.getIno();
    }

    @Override
    public PageResultDTO<ItemDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("ino").descending());

        Page<Object[]> result = itemRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], ItemDTO> fn = (arr -> entitiesDTO(
                (Item) arr[0] ,
                (List<ItemImage>)(Arrays.asList((ItemImage)arr[1])))

        );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ItemDTO read(Long ino) {

        List<Object[]> result = itemRepository.getItemWithAll(ino);

        Item item = (Item) result.get(0)[0];

        List<ItemImage> itemImgList = new ArrayList<>();

        result.forEach(arr -> {
            ItemImage itemImg = (ItemImage)arr[1];
            itemImgList.add(itemImg);
        });

        return entitiesDTO(item, itemImgList);

    }

    @Override
    public ItemDTO getItem(Long ino) {
        List<Object[]> result = itemRepository.getItemWithAll(ino);

        Item item = (Item) result.get(0)[0];

        List<ItemImage> itemImageList = new ArrayList<>();

        result.forEach(arr -> {
            ItemImage  itemImage = (ItemImage)arr[1];
            itemImageList.add(itemImage);
        });


        return entitiesDTO(item, itemImageList);
    }

    @Override
    public ItemDTO getMid(String mid) { //해당 아이디로 조회
        List<Object[]> result = itemRepository.getMidWithAll(mid);

        Item item = (Item) result.get(0)[0];

        List<ItemImage> itemImageList = new ArrayList<>();

        result.forEach(arr -> {
            ItemImage itemImage = (ItemImage)arr[1];
            itemImageList.add(itemImage);
        });

        return entitiesDTO(item, itemImageList);
    }

    @Override
    public ItemDTO getIname(String iname) { //해당 물품명으로 조회
        List<Object[]> result = itemRepository.getInameWithAll(iname);

        Item item = (Item) result.get(0)[0];

        List<ItemImage> itemImageList = new ArrayList<>();

        result.forEach(arr -> {
            ItemImage itemImage = (ItemImage)arr[1];
            itemImageList.add(itemImage);
        });

        return entitiesDTO(item, itemImageList);
    }

    @Override
    public void modify(ItemDTO itemDTO) {
        Optional<Item> result = itemRepository.findById(itemDTO.getIno());

        if(result.isPresent()){

            Item entity = result.get();

            entity.changeIname(itemDTO.getIname());
            entity.changeIprice(itemDTO.getIprice());
            entity.changeIsaleStatus(itemDTO.getIsaleStatus());
            entity.changeIcategory(itemDTO.getIcategory());
            entity.changeIdealway(itemDTO.getIdealway());
            entity.changeIlocation(itemDTO.getIlocation());
            entity.changeIdescription(itemDTO.getIdescription());
            entity.changeIcondition(itemDTO.getIcondition());

            itemRepository.save(entity);
        }

    }

    @Override
    public void remove(Long ino) {
        itemRepository.deleteById(ino);

    }

    @Override
    public Object count() {
        itemRepository.count();
        return itemRepository.count();
    }

    @Override
    // 사용자가 작성한 글만을 조회하는 메서드
    public List<Item> getItemsByMid(String mid) {
        return itemRepository.findByMember_Mid(mid);
    }
}
