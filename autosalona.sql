
USE autosalon;

-- 2. Таблица автомобилей
CREATE TABLE cars (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    type VARCHAR(50) NOT NULL,         
    model VARCHAR(50) NOT NULL,        
    brand VARCHAR(50) NOT NULL         
);

-- 3. Таблица покупателей
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(100) NOT NULL,        
    age INT NOT NULL,                  
    gender VARCHAR(10) NOT NULL        
);

-- 4. Таблица продаж с удалением
CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    car_id INT NOT NULL,               
    customer_id INT NOT NULL,          
    
    FOREIGN KEY (customer_id) REFERENCES customers(id) 
);
