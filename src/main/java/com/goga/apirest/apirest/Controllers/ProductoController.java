package com.goga.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goga.apirest.apirest.Entities.Producto;
import com.goga.apirest.apirest.Repositories.ProductoRepository;

// cambiamos @RestController por @Controller para poder manejar vistas Thymeleaf
@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping()
    public String getAllProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos";
    }

    @GetMapping("/{id}")
    public String getProductoById(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el id: " + id)));
        return "infoProducto";
    }

    @GetMapping("/agregar")
    public String getFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "agregarProducto";
    }

    @PostMapping
    public String createProducto(@ModelAttribute Producto producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/update/{id}")
    public String getFormularioUpdate(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el id: " + id));
        model.addAttribute("producto", producto);
        return "actualizarProducto";
    }

    @PostMapping("/{id}")
    public String updateProducto(@PathVariable Long id, @ModelAttribute Producto detallesProducto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el id: " + id));

        producto.setNombre(detallesProducto.getNombre());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setPrecio(detallesProducto.getPrecio());

        productoRepository.save(producto);

        return "redirect:/productos";
    }

    @GetMapping("delete/{id}")
    public String deleteProducto(Model model, @PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con el id: " + id));
        productoRepository.delete(producto);
        return "redirect:/productos";
    }
}
