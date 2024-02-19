package com.durianfirst.durian.service;

import com.durianfirst.durian.dto.ItemDTO;
import com.durianfirst.durian.dto.ItemImageDTO;
import com.durianfirst.durian.dto.PageRequestDTO;
import com.durianfirst.durian.dto.PageResultDTO;
import com.durianfirst.durian.entity.Item;
import com.durianfirst.durian.entity.ItemImage;
import com.durianfirst.durian.entity.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface ItemService {

    Long register(ItemDTO itemDTO); // 상품등록

    PageResultDTO<ItemDTO, Object[]> getList(PageRequestDTO requestDTO);

    ItemDTO getItem(Long ino);

    ItemDTO read(Long ino);

    ItemDTO getMid(String mid);

    ItemDTO getIname(String iname);

    void modify(ItemDTO itemDTO);

    void remove(Long ino);

    default ItemDTO entitiesDTO(Item item, List<ItemImage> itemImages) {
        ItemDTO itemDTO = ItemDTO.builder()
                .ino(item.getIno())
                .iname(item.getIname())
                .iprice(item.getIprice())
                .isaleStatus(item.getIsaleStatus())
                .icategory(item.getIcategory())
                .idealway(item.getIdealway())
                .ilocation(item.getIlocation())
                .idescription(item.getIdescription())
                .icondition(item.getIcondition())
                .mid(item.getMember().getMid())
                .regDate(item.getRegDate())
                .modDate(item.getModDate())
                .build();

        List<ItemImageDTO> itemImageDTOList = itemImages.stream().map(itemImage -> {
            return ItemImageDTO.builder().imgName(itemImage.getImgName())
                    .path(itemImage.getPath())
                    .uuid(itemImage.getUuid())
                    .build();

        }).collect(Collectors.toList());

        itemDTO.setImageDTOList(itemImageDTOList);

        return itemDTO;
    }


    default Map<String, Object> dtoToEntity(ItemDTO itemDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Item item = Item.builder()
                .ino(itemDTO.getIno())
                .iname(itemDTO.getIname())
                .iprice(itemDTO.getIprice())
                .isaleStatus(itemDTO.getIsaleStatus())
                .icategory(itemDTO.getIcategory())
                .idealway(itemDTO.getIdealway())
                .ilocation(itemDTO.getIlocation())
                .idescription(itemDTO.getIdescription())
                .icondition(itemDTO.getIcondition())
                .member(Member.builder().mid(itemDTO.getMid()).build())
                .build();
        entityMap.put("item", item);

        List<ItemImageDTO> imageDTOSList = itemDTO.getImageDTOList();

        if (imageDTOSList != null && imageDTOSList.size() > 0) {
            List<ItemImage> itemImageList = imageDTOSList.stream().map(itemImageDTO -> {
                ItemImage itemImage = ItemImage.builder()
                        .path(itemImageDTO.getPath())
                        .imgName(itemImageDTO.getImgName())
                        .uuid(itemImageDTO.getUuid())
                        .item(item)
                        .build();
                return itemImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", itemImageList);
        }

        return entityMap;
    }

    Object count();
    List<Item> getItemsByMid(String mid);

}
