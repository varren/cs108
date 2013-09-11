USE c_cs108_psyoung;

DROP TABLE IF EXISTS products;
 -- remove table if it already exists and start from scratch

CREATE TABLE products (
	productid CHAR(6),
    name CHAR(64),
    imagefile CHAR(64),
    price DECIMAL(6,2)
);

INSERT INTO products VALUES
	("HC","Classic Hoodie","Hoodie.jpg",40),
    ("HLE", "Limited Edition Hood","LimitedEdHood.jpg",54.95),
	("TC", "Classic Tee","TShirt.jpg",13.95),
	("TS","Seal Tee","SealTShirt.jpg",19.95),
	("TLJa","Japanese Tee","JapaneseTShirt.jpg",17.95),
	("TLKo","Korean Tee","KoreanTShirt.jpg",17.95),
	("TLCh","Chinese Tee","ChineseTShirt.jpg",17.95),
	("TLHi","Hindi Tee","HindiTShirt.jpg",17.95),
	("TLAr","Arabic Tee","ArabicTShirt.jpg",17.95),
	("TLHe","Hebrew Tee","HebrewTShirt.jpg",17.95),
	("AKy","Keychain","Keychain.jpg",2.95),
	("ALn","Lanyard","Lanyard.jpg",5.95),
	("ATherm","Thermos","Thermos.jpg",19.95),
	("AMinHm","Mini Football Helmet","MiniHelmet.jpg",29.95)
