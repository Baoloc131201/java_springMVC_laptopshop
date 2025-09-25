# java_springMVC_laptopshop
java_springMVC_laptopshop

<img width="3733" height="3845" alt="image" src="https://github.com/user-attachments/assets/61b61872-aaf9-4f6d-a67f-0618e868605e" />

🔷 1. Bảng users
Đại diện cho người dùng trong hệ thống.

Trường	Kiểu dữ liệu	Ý nghĩa
id	bigint, PK	Khóa chính
name	string	Tên người dùng
email	string	Email (unique)
password	string	Mật khẩu
fullName	string	Họ tên đầy đủ
address	string	Địa chỉ
phone	string	Số điện thoại
avatar	string	Đường dẫn ảnh đại diện
role_id	bigint, FK	Khóa ngoại đến roles

Quan hệ:

users belongs to roles → mỗi user có một role

users has many orders

users has one cart

🔷 2. Bảng roles
Vai trò của người dùng (admin, user, v.v.)

Trường	Kiểu dữ liệu	Ý nghĩa
id	bigint, PK	Khóa chính
name	string	Tên vai trò
description	text	Mô tả

Quan hệ:

roles has many users

🔷 3. Bảng orders
Thông tin đơn đặt hàng.

Trường	Kiểu	Ý nghĩa
id	bigint, PK	Mã đơn hàng
totalPrice	decimal	Tổng tiền
receiverName	string	Tên người nhận
receiverAddress	string	Địa chỉ nhận
receiverPhone	string	SĐT người nhận
status	string	Trạng thái
user_id	bigint, FK	FK đến users

Quan hệ:

orders belongs to users

orders has many order_detail

🔷 4. Bảng order_detail
Chi tiết từng sản phẩm trong một đơn hàng.

Trường	Kiểu	Ý nghĩa
id	bigint, PK	Mã chi tiết đơn
quantity	bigint	Số lượng
price	decimal	Giá
order_id	bigint, FK	FK đến orders
product_id	bigint, FK	FK đến products

Quan hệ:

order_detail belongs to orders

order_detail belongs to products

🔷 5. Bảng products
Sản phẩm có thể đặt.

Trường	Kiểu	Ý nghĩa
id	bigint, PK	Mã sản phẩm
name	string	Tên
price	decimal	Giá
image	string	Ảnh
detailDesc	text	Mô tả chi tiết
shortDesc	text	Mô tả ngắn
quantity	bigint	Số lượng trong kho
sold	bigint	Số lượng đã bán
factory	string	Nhà sản xuất
target	string	Đối tượng sử dụng

Quan hệ:

products used in many order_detail

products used in many cart_detail

🔷 6. Bảng carts
Giỏ hàng của user.

Trường	Kiểu	Ý nghĩa
id	bigint, PK	Mã giỏ
sum	int	Tổng tiền
user_id	bigint, FK	FK đến users

Quan hệ:

carts belongs to users (1-1)

carts has many cart_detail

🔷 7. Bảng cart_detail
Chi tiết từng sản phẩm trong giỏ hàng.

Trường	Kiểu	Ý nghĩa
id	bigint, PK	Mã chi tiết
quantity	bigint	Số lượng
price	decimal	Giá
cart_id	bigint, FK	FK đến carts
product_id	bigint, FK	FK đến products

🔗 Tóm tắt quan hệ chính:
User – Role: many-to-one

User – Order: one-to-many

User – Cart: one-to-one

Order – OrderDetail: one-to-many

Product – OrderDetail: one-to-many

Cart – CartDetail: one-to-many

Product – CartDetail: one-to-many
