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

import com.universidad.tecno.api_gestion_accesorios.dto.transfer.CreateTransferDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.CreateTransferDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.GetTransferDetailDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.GetTransferDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.ListTransferDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Transfer;
import com.universidad.tecno.api_gestion_accesorios.entities.TransferDetail;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;
import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;
import com.universidad.tecno.api_gestion_accesorios.repositories.TransferRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.TransferService;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Override
    public Page<ListTransferDto> paginateAll(Pageable pageable) {
        Page<Transfer> transfersPage = transferRepository.findAll(pageable);

        return transfersPage.map(t -> new ListTransferDto(
                t.getId(),
                t.getDescription(),
                t.getDate(),
                t.getUser().getName()));
    }

    @Override
    public List<ListTransferDto> findAll() {
        List<Transfer> transfers = (List<Transfer>) transferRepository.findAll();
        return transfers.stream()
                .map(t -> new ListTransferDto(
                        t.getId(),
                        t.getDescription(),
                        t.getDate(),
                        t.getUser().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GetTransferDto> findById(Long id) {
        return transferRepository.findById(id).map(transfer -> {
            List<GetTransferDetailDto> detailDtos = transfer.getTransferDetails().stream()
                    .map(detail -> new GetTransferDetailDto(
                            detail.getWarehouseDetail().getAccessory().getId(),
                            detail.getWarehouseDetail().getAccessory().getName(),
                            detail.getQuantity(),
                            detail.getWarehouseDetail().getStock()))
                    .collect(Collectors.toList());

            return new GetTransferDto(
                    transfer.getId(),
                    transfer.getDescription(),
                    transfer.getDate(),
                    transfer.getOriginWarehouse().getName(),
                    transfer.getDestinationWarehouse().getName(),
                    transfer.getUser().getName(),
                    detailDtos);
        });
    }

    @Override
    public Transfer createTransfer(CreateTransferDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Warehouse origin = warehouseRepository.findById(dto.getOriginWarehouseId())
                .orElseThrow(() -> new RuntimeException("Almacén de origen no encontrado"));

        Warehouse destination = warehouseRepository.findById(dto.getDestinationWarehouseId())
                .orElseThrow(() -> new RuntimeException("Almacén de destino no encontrado"));

        if (origin.getId().equals(destination.getId())) {
            throw new RuntimeException("El almacén de origen y destino no pueden ser iguales");
        }

        Transfer transfer = new Transfer();
        transfer.setDate(LocalDateTime.now());
        transfer.setDescription(dto.getDescription());
        transfer.setUser(user);
        transfer.setOriginWarehouse(origin);
        transfer.setDestinationWarehouse(destination);

        List<TransferDetail> detailList = new ArrayList<>();

        for (CreateTransferDetailDto detailDto : dto.getTransferDetails()) {
            WarehouseDetail originDetail = warehouseDetailRepository.findById(detailDto.getWarehouseDetailId())
                    .orElseThrow(() -> new RuntimeException("Detalle de almacén no encontrado"));

            if (originDetail.getStock() < detailDto.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para el accesorio con ID " + originDetail.getAccessory().getId());
            }

            // Actualizar stock del almacén de origen
            originDetail.setStock(originDetail.getStock() - detailDto.getQuantity());

            // Obtener o crear el detalle del almacén de destino para ese accesorio
            WarehouseDetail destinationDetail = warehouseDetailRepository
                    .findByAccessoryIdAndWarehouseId(originDetail.getAccessory().getId(), destination.getId())
                    .orElseGet(() -> {
                        WarehouseDetail newDetail = new WarehouseDetail();
                        newDetail.setAccessory(originDetail.getAccessory());
                        newDetail.setWarehouse(destination);
                        newDetail.setStock(0);
                        newDetail.setState("Activo");
                        return newDetail;
                    });

            destinationDetail.setStock(destinationDetail.getStock() + detailDto.getQuantity());

            warehouseDetailRepository.save(destinationDetail);

            TransferDetail transferDetail = new TransferDetail();
            transferDetail.setTransfer(transfer);
            transferDetail.setWarehouseDetail(originDetail);
            transferDetail.setQuantity(detailDto.getQuantity());

            detailList.add(transferDetail);
        }

        transfer.setTransferDetails(detailList);

        return transferRepository.save(transfer);
    }

    @Override
    public boolean deleteTransfer(Long id) {
        Optional<Transfer> existingTransfer = transferRepository.findById(id);

        if (existingTransfer.isPresent()) {
            transferRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
