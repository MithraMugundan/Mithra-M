package com.hexaware.egs.presentation;

import com.hexaware.egs.entity.Customers;
import com.hexaware.egs.entity.Products;
import com.hexaware.egs.entity.OrderDetails;
import com.hexaware.egs.entity.Orders;
import com.hexaware.egs.entity.Inventory;
import com.hexaware.egs.exception.*;
import com.hexaware.egs.service.*;

import java.io.IOException;
import java.util.Scanner;

public class ClientUI {
	static Scanner sc;

	static {
		sc = new Scanner(System.in);
	}

	public ClientUI() {
	}

	public static void main(String[] args) throws SqlException, IOException, ConcurrencyException,
			PaymentFailedException, IncompleteOrderException, InsufficientStockException {
		ICustomerService customerService = new CustomerServiceImp();
		IProductService productService = new ProductServiceImp();
		IOrderService orderService = new OrderServiceImp();
		IOrderDetailsService orderDetailsService = new OrderDetailsServiceImp(); 
		IInventoryService inventoryService = new InventoryServiceImp(); 
		boolean flag = true;

		while (flag) {
			System.out.println("1. CUSTOMER OPERATIONS");
			System.out.println("2. PRODUCT OPERATIONS");
			System.out.println("3. ORDER OPERATIONS");
			System.out.println("4. ORDER DETAILS OPERATIONS");
			System.out.println("5. INVENTORY OPERATIONS"); 
			System.out.println("0. EXIT");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				flag = false;
				System.out.println("Thank you, Visit Again..");
				break;

			case 1:
				handleCustomerOperations(customerService);
				break;

			case 2:
				handleProductOperations(productService);
				break;

			case 3:
				handleOrderOperations(orderService);
				break;

			case 4:
				handleOrderDetailsOperations(orderDetailsService); 
				break;

			case 5:
				handleInventoryOperations(inventoryService); 
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private static void handleCustomerOperations(ICustomerService service) {
		boolean customerFlag = true;

		while (customerFlag) {
			System.out.println("1. GET CUSTOMER DETAILS");
			System.out.println("2. UPDATE CUSTOMER INFO");
			System.out.println("3. CALCULATE TOTAL ORDERS");
			System.out.println("0. BACK");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				customerFlag = false;
				break;

			case 1:
				System.out.println("Enter Customer ID to View Details:");
				int custId = sc.nextInt();
				Customers customer = null;

				try {
					customer = service.getCustomerDetails(custId);
				} catch (CustomerNotFoundException ex) {
					System.err.println("Sorry, Customer Not Found with ID " + custId);
				}

				if (customer != null) {
					System.out.println(customer);
				}
				break;

			case 2:
				Customers updatedCustomer = readCustomerData();
				boolean isValid = CustomerServiceImp.validateCustomer(updatedCustomer);
				if (isValid) {
					int rows = service.updateCustomerInfo(updatedCustomer);
					System.out.println(rows + " record(s) updated successfully.");
				} else {
					System.err.println("Invalid Customer Data.");
				}
				break;

			case 3:
				System.out.println("Enter Customer ID to Calculate Orders:");
				int custIdForOrders = sc.nextInt();
				int totalOrders = service.calculateTotalOrders(custIdForOrders);
				System.out.println("Total Orders Placed: " + totalOrders);
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private static void handleProductOperations(IProductService service) {
		boolean productFlag = true;

		while (productFlag) {
			System.out.println("1. GET PRODUCT DETAILS");
			System.out.println("2. UPDATE PRODUCT INFO");
			System.out.println("3. CHECK IF PRODUCT IS IN STOCK");
			System.out.println("0. BACK");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				productFlag = false;
				break;

			case 1:
				System.out.println("Enter Product ID to View Details:");
				int prodId = sc.nextInt();
				Products product = null;

				try {
					product = service.getProductDetails(prodId);
				} catch (ProductNotFoundException ex) {
					System.err.println("Sorry, Product Not Found with ID " + prodId);
				}

				if (product != null) {
					System.out.println(product);
				}
				break;

			case 2:
				Products updatedProduct = readProductData();
				boolean isValidProduct = ProductServiceImp.validateProduct(updatedProduct);
				if (isValidProduct) {
					int rows = service.updateProductInfo(updatedProduct);
					System.out.println(rows + " record(s) updated successfully.");
				} else {
					System.err.println("Invalid Product Data.");
				}
				break;

			case 3:
				System.out.println("Enter Product ID to Check Stock:");
				int prodIdForStock = sc.nextInt();
				boolean isInStock = service.isProductInStock(prodIdForStock);
				System.out.println("Is Product in Stock? " + (isInStock ? "Yes" : "No"));
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private static void handleOrderOperations(IOrderService service)
			throws SqlException, IOException, ConcurrencyException {
		boolean orderFlag = true;

		while (orderFlag) {
			System.out.println("1. CALCULATE TOTAL AMOUNT OF ORDER");
			System.out.println("2. GET ORDER DETAILS");
			System.out.println("3. UPDATE ORDER STATUS");
			System.out.println("4. CANCEL ORDER");
			System.out.println("0. BACK");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				orderFlag = false;
				break;

			case 1:
				System.out.println("Enter Order ID to calculate total amount:");
				int orderId1 = sc.nextInt();
				try {
					double total = service.calculateTotalAmount(orderId1);
					System.out.println("Total Order Amount: ₹" + total);
				} catch (InvalidDataException e) {
					System.err.println("Invalid Order Data: " + e.getMessage());
				}
				break;

			case 2:
				System.out.println("Enter Order ID to view details:");
				int orderId2 = sc.nextInt();
				try {
					Orders order = service.getOrderDetails(orderId2);
					System.out.println(order);
				} catch (IncompleteOrderException e) {
					System.err.println("Incomplete Order: " + e.getMessage());
				}
				break;

			case 3:
				System.out.println("Enter Order ID to update status:");
				int orderId3 = sc.nextInt();
				sc.nextLine(); // consume newline
				System.out.println("Enter New Status (e.g., Processing, Shipped):");
				String status = sc.nextLine();
				try {
					int updated = service.updateOrderStatus(orderId3, status);
					System.out.println(updated + " record(s) updated.");
				} catch (InvalidDataException e) {
					System.err.println("Error updating order status: " + e.getMessage());
				}
				break;

			case 4:
				System.out.println("Enter Order ID to cancel:");
				int orderId4 = sc.nextInt();
				try {
					int cancelled = service.cancelOrder(orderId4);
					System.out.println("Order cancelled successfully.");
				} catch (InsufficientStockException e) {
					System.err.println("Error cancelling order: " + e.getMessage());
				}
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	private static void handleOrderDetailsOperations(IOrderDetailsService service)
			throws SqlException, IOException, ConcurrencyException, PaymentFailedException, IncompleteOrderException {
		boolean orderDetailFlag = true;

		while (orderDetailFlag) {
			System.out.println("1. CALCULATE SUBTOTAL");
			System.out.println("2. GET ORDER DETAIL INFO");
			System.out.println("3. UPDATE QUANTITY");
			System.out.println("4. ADD DISCOUNT");
			System.out.println("0. BACK");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				orderDetailFlag = false;
				break;

			case 1:
				System.out.println("Enter Order Detail ID to calculate subtotal:");
				int orderDetailId1 = sc.nextInt();
				try {
					double subtotal = service.calculateSubtotal(orderDetailId1);
					System.out.println("Subtotal: ₹" + subtotal);
				} catch (InvalidDataException e) {
					System.err.println("Invalid Data: " + e.getMessage());
				}
				break;

			case 2:
				System.out.println("Enter Order Detail ID to get information:");
				int orderDetailId2 = sc.nextInt();
				try {
					OrderDetails orderDetail = service.getOrderDetailInfo(orderDetailId2);
					System.out.println(orderDetail);
				} catch (SqlException e) {
					System.err.println("Error fetching order details: " + e.getMessage());
				}
				break;

			case 3:
				System.out.println("Enter Order Detail ID to update quantity:");
				int orderDetailId3 = sc.nextInt();
				System.out.println("Enter New Quantity:");
				int newQuantity = sc.nextInt();
				try {
					int updated = service.updateQuantity(orderDetailId3, newQuantity);
					System.out.println(updated + " record(s) updated.");
				} catch (InvalidDataException | InsufficientStockException | ConcurrencyException e) {
					System.err.println("Error updating quantity: " + e.getMessage());
				}
				break;

			case 4:
				System.out.println("Enter Order Detail ID to apply discount:");
				int orderDetailId4 = sc.nextInt();
				System.out.println("Enter Discount Percent:");
				double discountPercent = sc.nextDouble();
				try {
					boolean discountApplied = service.addDiscount(orderDetailId4, discountPercent);
					System.out
							.println(discountApplied ? "Discount applied successfully." : "Failed to apply discount.");
				} catch (InvalidDataException | SqlException e) {
					System.err.println("Error applying discount: " + e.getMessage());
				}
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	// New method to handle Inventory operations
	private static void handleInventoryOperations(IInventoryService service)
			throws SqlException, ConcurrencyException, InsufficientStockException {
		boolean inventoryFlag = true;

		while (inventoryFlag) {
			System.out.println("1. GET PRODUCT FROM INVENTORY");
			System.out.println("2. CHECK STOCK QUANTITY");
			System.out.println("3. ADD TO INVENTORY");
			System.out.println("4. REMOVE FROM INVENTORY");
			System.out.println("5. UPDATE STOCK QUANTITY");
			System.out.println("6. CHECK PRODUCT AVAILABILITY");
			System.out.println("7. GET INVENTORY VALUE");
			System.out.println("8. LIST LOW STOCK PRODUCTS");
			System.out.println("9. LIST OUT OF STOCK PRODUCTS");
			System.out.println("0. BACK");

			int choice = sc.nextInt();
			switch (choice) {
			case 0:
				inventoryFlag = false;
				break;

			case 1:
				System.out.println("Enter Inventory ID to fetch product details:");
				int invId1 = sc.nextInt();
				try {
					Products product = service.getProduct(invId1);
					System.out.println("Product Details: " + product);
				} catch (InvalidDataException | SqlException e) {
					System.err.println("Error fetching product: " + e.getMessage());
				}
				break;

			case 2:
				System.out.println("Enter Inventory ID to check stock quantity:");
				int invId2 = sc.nextInt();
				try {
					int quantity = service.getQuantityInStock(invId2);
					System.out.println("Stock Quantity: " + quantity);
				} catch (InvalidDataException | SqlException e) {
					System.err.println("Error fetching quantity: " + e.getMessage());
				}
				break;

			case 3:
				System.out.println("Enter Inventory ID to add to inventory:");
				int invId3 = sc.nextInt();
				System.out.println("Enter Quantity to Add:");
				int addQty = sc.nextInt();
				try {
					int updated = service.addToInventory(invId3, addQty);
					System.out.println(updated + " record(s) updated.");
				} catch (InvalidDataException | SqlException | ConcurrencyException e) {
					System.err.println("Error updating inventory: " + e.getMessage());
				}
				break;

			case 4:
				System.out.println("Enter Inventory ID to remove from inventory:");
				int invId4 = sc.nextInt();
				System.out.println("Enter Quantity to Remove:");
				int removeQty = sc.nextInt();
				try {
					int updated = service.removeFromInventory(invId4, removeQty);
					System.out.println(updated + " record(s) updated.");
				} catch (SqlException | ConcurrencyException e) {
					System.err.println("Error removing from inventory: " + e.getMessage());
				}
				break;

			case 5:
				System.out.println("Enter Inventory ID to update stock quantity:");
				int invId5 = sc.nextInt();
				System.out.println("Enter New Stock Quantity:");
				int newStockQty = sc.nextInt();
				try {
					int updated = service.updateStockQuantity(invId5, newStockQty);
					System.out.println(updated + " record(s) updated.");
				} catch (InvalidDataException | SqlException | ConcurrencyException e) {
					System.err.println("Error updating stock quantity: " + e.getMessage());
				}
				break;

			case 6:
				System.out.println("Enter Inventory ID to check product availability:");
				int invId6 = sc.nextInt();
				System.out.println("Enter Quantity to Check Availability:");
				int quantityToCheck = sc.nextInt();
				try {
					boolean isAvailable = service.isProductAvailable(invId6, quantityToCheck);
					System.out.println("Is product available? " + (isAvailable ? "Yes" : "No"));
				} catch (SqlException e) {
					System.err.println("Error checking availability: " + e.getMessage());
				}
				break;

			case 8:
				System.out.println("Enter Low Stock Threshold Quantity:");
				int threshold = sc.nextInt();
				try {
					System.out.println("Low Stock Products:");
					for (Inventory inv : service.listLowStockProducts(threshold)) {
						System.out.println(inv);
					}
				} catch (SqlException e) {
					System.err.println("Error listing low stock products: " + e.getMessage());
				}
				break;

			case 9:
				try {
					System.out.println("Out of Stock Products:");
					for (Inventory inv : service.listOutOfStockProducts()) {
						System.out.println(inv);
					}
				} catch (SqlException e) {
					System.err.println("Error listing out of stock products: " + e.getMessage());
				}
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}

	public static Customers readCustomerData() {
		sc.nextLine(); // consume newline
		System.out.println("Enter Customer ID:");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter First Name:");
		String name = sc.nextLine();
		System.out.println("Enter Last Name:");
		String lname = sc.nextLine();
		System.out.println("Enter Email:");
		String email = sc.nextLine();
		System.out.println("Enter Phone:");
		String phone = sc.nextLine();
		System.out.println("Enter Address:");
		String address = sc.nextLine();

		Customers customer = new Customers();
		customer.setCustomerId(id);
		customer.setFirstName(name);
		customer.setLastName(lname);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);
		return customer;
	}

	public static Products readProductData() {
		sc.nextLine(); // consume newline
		System.out.println("Enter Product ID:");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Product Name:");
		String name = sc.nextLine();
		System.out.println("Enter Product Description:");
		String description = sc.nextLine();
		System.out.println("Enter Product Price:");
		double price = sc.nextDouble();

		Products product = new Products();
		product.setProductId(id);
		product.setProductName(name);
		product.setDescription(description);
		product.setPrice(price);
		return product;
	}
}
