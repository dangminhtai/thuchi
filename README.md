# Quản lý Thu Chi - Android App

Ứng dụng giúp người dùng quản lý các khoản thu nhập và chi tiêu hàng ngày một cách trực quan qua biểu đồ và lịch.

## Tính năng
- Nhập thu chi với các danh mục (ăn uống, mua sắm,...).
- Xem báo cáo tháng với biểu đồ donut.
- Xem lịch sử giao dịch qua lịch.

## Cấu trúc dự án
- `com.example.thu_chi.data`: Lớp dữ liệu (Room DB, Entities, DAO).
- `com.example.thu_chi.ui`: Các Activity, Fragment và ViewModel.
- `com.example.thu_chi.repository`: Lớp trung gian xử lý dữ liệu.

## Hướng dẫn cài đặt
1. Mở dự án trong Android Studio (phiên bản mới nhất).
2. Chạy lệnh `gradle sync` để tải các dependencies.
3. Chạy ứng dụng trên thiết bị Android hoặc Emulator.
