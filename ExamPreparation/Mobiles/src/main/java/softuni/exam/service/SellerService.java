package softuni.exam.service;

import softuni.exam.models.entity.Seller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

// TODO: Implement all methods
public interface SellerService {

    boolean areImported();

    String readSellersFromFile() throws IOException;

    String importSellers() throws IOException;

    Seller findSellerById(Long sellerId);

    void saveAddedSaleToSeller(Seller seller);

    Seller findSellerById(long seller);

//    Seller findSellerById(Long sellerId);
//
//    void saveAddedSaleToSeller(Seller seller);
}
