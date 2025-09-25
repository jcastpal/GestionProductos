package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsAllProductos() {
        Producto p1 = new Producto("Teclado", 10);
        Producto p2 = new Producto("Mouse", 5);
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> productos = productoService.getAll();
        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void getById_returnsProducto() {
        Producto p = new Producto("Teclado", 10);
        when(productoRepository.findById("1")).thenReturn(Optional.of(p));

        Optional<Producto> result = productoService.getById("1");
        assertTrue(result.isPresent());
        assertEquals("Teclado", result.get().getNombre());
    }

    @Test
    void save_returnsSavedProducto() {
        Producto p = new Producto("Teclado", 10);
        when(productoRepository.save(p)).thenReturn(p);

        Producto result = productoService.save(p);
        assertEquals("Teclado", result.getNombre());
        verify(productoRepository, times(1)).save(p);
    }

    @Test
    void update_returnsUpdatedProducto() {
        Producto p = new Producto("Teclado", 10);
        p.setId("1");
        when(productoRepository.save(p)).thenReturn(p);

        Producto result = productoService.update("1", p);
        assertEquals("1", result.getId());
        verify(productoRepository, times(1)).save(p);
    }

    @Test
    void deactivate_setsProductoAsInactive() {
        Producto p = new Producto("Teclado", 10);
        p.setId("1");
        when(productoRepository.findById("1")).thenReturn(Optional.of(p));
        when(productoRepository.save(p)).thenReturn(p);

        productoService.deactivate("1");
        assertFalse(p.isActivo());
        verify(productoRepository, times(1)).save(p);
    }
}
