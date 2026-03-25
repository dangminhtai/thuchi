# Yêu cầu dự án (Requirements)

## [REQ001] Quản lý Thu Chi Cơ Bản
- Người dùng có thể nhập số tiền, ghi chú, ngày tháng.
- Lựa chọn danh mục (Ăn uống, Tiền điện, Mỹ phẩm, etc.).
- Lưu trữ dữ liệu lâu dài bằng Room Database.

## [REQ002] Báo cáo Biểu đồ Tròn
- Hiển thị theo tháng hiện tại.
- Phân bổ chi tiêu theo phần trăm giữa các danh mục.
- Hiển thị số tiền cụ thể của từng mục.
- Giao diện trực quan (Donut Chart) với tổng chi ở giữa.

## [REQ003] Xem Lịch sử Giao dịch
- Giao diện lịch (CalendarView) để chọn ngày.
- Danh sách giao dịch chi tiết theo ngày đã chọn.
- Thống kê tóm tắt (Thu/Chi/Tổng) của ngày đó.

## [REQ004] Quản lý Ngân sách (Budgeting)
- Thiết lập hạn mức chi tiêu cho từng danh mục theo tháng.
- Hiển thị thanh tiến trình (Progress Bar) trong báo cáo.
- Cảnh báo (Màu đỏ) khi chi tiêu vượt quá hạn mức.

## [REQ005] Bảo mật Ứng dụng
- Khóa app bằng mã PIN (4 chữ số).
- Hỗ trợ mở khóa bằng vân tay (Biometric Authentication).
- Giao diện bàn phím số tùy chỉnh và phản hồi tinh tế.

## [REQ006] Xuất báo cáo dữ liệu
- Hỗ trợ xuất toàn bộ dữ liệu giao dịch trong tháng ra file CSV.
- Chia sẻ file qua Email, Zalo, hoặc các nền tảng mạng xã hội khác.
