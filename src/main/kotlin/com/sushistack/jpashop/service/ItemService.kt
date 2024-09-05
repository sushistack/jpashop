package com.sushistack.jpashop.service

import com.sushistack.jpashop.domain.item.Item
import com.sushistack.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ItemService(private val itemRepository: ItemRepository) {

    fun saveItem(item: Item) = itemRepository.save(item)

    fun updateItem(itemId: Long, name: String, price: Int, stockQuantity: Int) =
        itemRepository.findById(itemId).getOrNull()?.let {
            it.name = name
            it.price = price
            it.stockQuantity = stockQuantity
        }

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item? = itemRepository.findById(itemId).orElse(null)
}