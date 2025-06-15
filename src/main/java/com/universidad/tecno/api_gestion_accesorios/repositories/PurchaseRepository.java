package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    Page<Purchase> findAll(Pageable pageable);

    // DASHBOARD
    @Query("SELECT SUM(p.totalAmount) FROM Purchase p WHERE FUNCTION('MONTH', p.purchaseDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', p.purchaseDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Double getMonthlyPurchasesAmount();

    @Query("SELECT SUM(pd.quantityType) FROM PurchaseDetail pd WHERE FUNCTION('MONTH', pd.purchase.purchaseDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', pd.purchase.purchaseDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Long getUnitsPurchasedThisMonth();
}

