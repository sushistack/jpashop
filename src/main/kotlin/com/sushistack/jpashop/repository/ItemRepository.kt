package com.sushistack.jpashop.repository

import com.sushistack.jpashop.domain.item.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long>