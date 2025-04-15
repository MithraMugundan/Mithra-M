package com.hexaware.egs.service;

import com.hexaware.egs.dao.ProductsDaoImp;
import com.hexaware.egs.dao.IProductsDao;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.exception.ProductNotFoundException;

public class ProductServiceImp implements IProductService {
   IProductsDao dao = new ProductsDaoImp();

   public ProductServiceImp() {
   }

   public Products getProductDetails(int productId) throws ProductNotFoundException {
      return this.dao.getProductDetails(productId);
   }

   public int updateProductInfo(Products product) {
      return this.dao.updateProductInfo(product);
   }

   public boolean isProductInStock(int productId) {
      return this.dao.isProductInStock(productId);
   }

   public static boolean validateProduct(Products product) {
      boolean flag = false;
      if (product.getProductName().length() > 3 &&
          product.getDescription().length() > 5 &&
          product.getPrice() > 0) {
         flag = true;
      }
      return flag;
   }
}
