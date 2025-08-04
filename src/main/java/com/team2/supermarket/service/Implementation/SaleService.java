package com.team2.supermarket.service.Implementation;


import com.team2.supermarket.dto.SaleDto;
import com.team2.supermarket.entity.Product;
import com.team2.supermarket.entity.Sale;
import com.team2.supermarket.repository.Productrepository;
import com.team2.supermarket.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    Productrepository productrepo;
    @Autowired
    SaleRepository saleRepo;


    public void makeSale(SaleDto saleDto) {


       // بتاكد من النتج هو موجود ولا لا
        Product product = productrepo.findById(saleDto.getProductId())
                .orElseThrow(()->new RuntimeException("product not found"));

        // بتاكده انو الكميه الي بيدخلها المستخدم هي اقل من Stok بتاع المنتج ولا لا
        if(product.getStock() < saleDto.getQuantity()){
        throw new RuntimeException("product not enough");
        }
        // خصم الكميه من المنتج
        product.setStock(product.getStock() - saleDto.getQuantity());
        productrepo.save(product);
        // create Sale
        Sale sale = new Sale();
        saleRepo.save(sale);


    }



}
