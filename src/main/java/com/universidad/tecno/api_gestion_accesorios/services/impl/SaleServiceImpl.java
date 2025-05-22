package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Client;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;
import com.universidad.tecno.api_gestion_accesorios.entities.SaleDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.ClientRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SaleDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SaleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SaleService;

import jakarta.transaction.Transactional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Sale> findAll() {
        return (List<Sale>) saleRepository.findAll();
    }

    @Override
    public Page<ListSaleDto> paginateAll(Pageable pageable) {
        Page<Sale> salesPage = saleRepository.findAll(pageable);

        return salesPage.map(sale -> {
            ListSaleDto dto = new ListSaleDto();
            dto.setId(sale.getId());
            dto.setTotalAmount(sale.getTotalAmount());
            dto.setTotalQuantity(sale.getTotalQuantity());
            dto.setSaleDate(sale.getSaleDate());
            dto.setClientId(sale.getClient().getId());
            dto.setUserId(sale.getUser().getId());
            return dto;
        });
    }

    @Override
    public List<ListSaleDto> getAllSales() {
        List<Sale> sales = (List<Sale>) saleRepository.findAll();

        return sales.stream().map(sale -> {
            ListSaleDto dto = new ListSaleDto();
            dto.setId(sale.getId());
            dto.setTotalAmount(sale.getTotalAmount());
            dto.setTotalQuantity(sale.getTotalQuantity());
            dto.setSaleDate(sale.getSaleDate());
            dto.setClientId(sale.getClient().getId());
            dto.setUserId(sale.getUser().getId());
            return dto;
        }).toList();
    }

    @Override
    public Optional<GetSaleDto> getSaleById(Long id) {
        // Obtener la venta
        Optional<Sale> saleOptional = saleRepository.findById(id);

        if (!saleOptional.isPresent()) {
            return Optional.empty(); // Si no se encuentra la venta, devolver Optional vacío
        }

        Sale sale = saleOptional.get();

        // Obtener cliente y usuario
        Client client = clientRepository.findById(sale.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        User user = userRepository.findById(sale.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapear los detalles de la venta
        List<GetSaleDetailDto> saleDetails = sale.getSaleDetails().stream().map(saleDetail -> {
            // Obtener accesorio por ID
            Accessory accessory = accessoryRepository.findById(saleDetail.getWarehouseDetail().getAccessory().getId())
                    .orElseThrow(() -> new RuntimeException("Accesorio no encontrado"));

            // Crear SaleDetailDto
            GetSaleDetailDto saleDetailDto = new GetSaleDetailDto();
            saleDetailDto.setAccessoryId(accessory.getId());
            saleDetailDto.setAccessoryName(accessory.getName());
            saleDetailDto.setQuantityType(saleDetail.getQuantityType());
            saleDetailDto.setAmountType(saleDetail.getAmountType());
            saleDetailDto.setPrice(accessory.getPrice());

            return saleDetailDto;
        }).collect(Collectors.toList());

        // Crear SaleDto
        GetSaleDto saleDto = new GetSaleDto();
        saleDto.setId(sale.getId());
        saleDto.setTotalAmount(sale.getTotalAmount());
        saleDto.setTotalQuantity(sale.getTotalQuantity());
        saleDto.setSaleDate(sale.getSaleDate());
        saleDto.setClientName(client.getName());
        saleDto.setClientEmail(client.getEmail());
        saleDto.setUserName(user.getName());
        saleDto.setSaleDetails(saleDetails);

        return Optional.of(saleDto); // Devolver el SaleDto envuelto en Optional
    }

    //NOTA: LA EL ACCESORIO TIENE QUE ESTAR OBLLIGATORIAMENTE EN EL ALMACEN 1 PARA LA VENTA
    @Transactional
    public Sale createSale(CreateSaleDto saleDto) {
        // Obtener cliente y usuario
        Client client = clientRepository.findById(saleDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        User user = userRepository.findById(saleDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear la venta
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setUser(user);
        sale.setSaleDate(LocalDateTime.now());

        // Guardar la venta
        sale = saleRepository.save(sale);

        // Lista de detalles de venta
        List<SaleDetail> saleDetails = new ArrayList<>();
        Double totalAmount = 0.0;
        Integer totalQuantity = 0;

        // Procesar los detalles de la venta
        for (CreateSaleDetailDto saleDetailDto : saleDto.getSaleDetails()) {
            Accessory accessory = accessoryRepository.findById(saleDetailDto.getAccessoryId())
                    .orElseThrow(() -> new RuntimeException("Accesorio no encontrado"));

            // Obtener el detalle de inventario del almacén con ID 1
            WarehouseDetail warehouseDetail = warehouseDetailRepository
                    .findByWarehouseIdAndAccessoryId(1L, accessory.getId())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el accesorio"));

            // Verificar stock suficiente
            if (warehouseDetail.getStock() < saleDetailDto.getQuantityType()) {
                throw new RuntimeException("Stock insuficiente");
            }

            // Actualizar stock en el inventario
            warehouseDetail.setStock(warehouseDetail.getStock() - saleDetailDto.getQuantityType());
            warehouseDetailRepository.save(warehouseDetail);

            // Calcular el monto total por producto (amountType = precio * cantidad)
            Double amountType = accessory.getPrice() * saleDetailDto.getQuantityType();
            totalAmount += amountType;
            totalQuantity += saleDetailDto.getQuantityType();

            // Crear y guardar el detalle de la venta
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setSale(sale);
            saleDetail.setWarehouseDetail(warehouseDetail);
            saleDetail.setAmountType(amountType);
            saleDetail.setQuantityType(saleDetailDto.getQuantityType());
            saleDetails.add(saleDetail);
        }

        // Guardar todos los detalles de la venta
        saleDetailRepository.saveAll(saleDetails);

        // Actualizar el total de la venta
        sale.setTotalAmount(totalAmount);
        sale.setTotalQuantity(totalQuantity);
        saleRepository.save(sale);

        return sale;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Sale> exsitingSale = saleRepository.findById(id);

        if (exsitingSale.isPresent()) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
