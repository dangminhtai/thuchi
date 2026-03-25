# 💰 ThuChi - Ứng dụng Quản lý Tài chính Cá nhân Premium

**ThuChi** là một ứng dụng Android hiện đại, giúp bạn theo dõi thu nhập và chi phí hàng ngày một cách thông minh, bảo mật và trực quan. Ứng dụng được xây dựng trên nền tảng **MVVM** và **Material Design 3**, mang lại trải nghiệm người dùng mượt mà và chuyên nghiệp.

---

## ✨ Tính năng Nổi bật

### 🔒 Bảo mật Tuyệt đối
- **Khóa mã PIN**: Bảo vệ dữ liệu của bạn bằng mã PIN 4 chữ số (Mặc định: `1234`).
- **Xác thực Sinh trắc học**: Đăng nhập nhanh chóng và an toàn bằng vân tay hoặc nhận diện khuôn mặt (`Biometric Authentication`).

### 📊 Báo cáo & Phân tích Đa dạng
- **Hệ thống Biểu đồ Kép**: Chuyển đổi linh hoạt giữa biểu đồ **Tròn (Pie Chart)** và biểu đồ **Cột (Bar Chart)** để phân tích tỷ lệ và mức độ chi tiêu.
- **Theo dõi Ngân sách**: Thiết lập hạn mức chi tiêu cho từng danh mục. Thanh tiến trình (`ProgressBar`) sẽ tự động cảnh báo đỏ khi bạn vượt quá ngân sách đã đặt.
- **Xuất Báo cáo CSV**: Dễ dàng xuất toàn bộ giao dịch trong tháng ra file CSV và chia sẻ qua Email, Zalo hoặc các nền tảng khác.

### 📁 Quản lý Danh mục Tùy chỉnh (MỚI)
- **Tự do Sáng tạo**: Thêm, sửa hoặc xóa danh mục chi tiêu với tên và biểu tượng Emoji riêng.
- **Phân loại Thông minh**: Hệ thống danh mục được lưu trữ bền vững trong cơ sở dữ liệu (`Room DB`).

### 📅 Lịch sử & Thống kê
- **Lịch Giao dịch**: Theo dõi chi tiết các khoản thu chi theo từng ngày trên giao diện lịch trực quan.
- **Dữ liệu Mẫu (Seeding)**: Hệ thống tự động khởi tạo các danh mục và giao dịch mẫu để bạn bắt đầu ngay lập tức.

---

## 🛠️ Công nghệ Sử dụng

- **Ngôn ngữ**: [Kotlin](https://kotlinlang.org/)
- **Kiến trúc**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **UI Components**: Material Components for Android v3
- **Biểu đồ**: [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- **Điều hướng**: Jetpack Navigation Component
- **Xử lý Bất đồng bộ**: Coroutines & Flow

---

## 📂 Cấu trúc Dự án (SOLID Principles)

```bash
com.example.thu_chi
├── data                # Room database, Entities (Transaction, Budget, Category), DAOs
├── repository          # TransactionRepository (Lớp trung gian xử lý logic dữ liệu)
├── ui                  # Các Fragment (Báo cáo, Nhập, Lịch, Quản lý Danh mục) & ViewModel
│   ├── adapter         # Các RecyclerView Adapter (CategoryAdapter, ReportAdapter,...)
│   └── LockActivity    # Màn hình bảo mật PIN/Sinh trắc học
├── util                # Các tiện ích (CsvExporter, SecurityUtils,...)
└── MainActivity        # Gatekeeper xử lý luồng điều hướng và bảo mật
```

---

## 🚀 Hướng dẫn Cài đặt

1.  **Clone dự án**:
    ```bash
    git clone https://github.com/dangminhtai/thuchi.git
    ```
2.  **Mở trong Android Studio**: Đảm bảo bạn đang sử dụng phiên bản Android Studio Giraffe hoặc mới hơn.
3.  **Sync Gradle**: Nhấn `Sync Now` để tải các dependencies cần thiết.
4.  **Chạy ứng dụng**: Kết nối thiết bị hoặc chạy Emulator (API Level 26+ để hỗ trợ đầy đủ tính năng Biometric).

---

## 📖 Tài liệu liên quan

- 📝 [Requirements.md](docs/requirements.md): Danh sách các yêu cầu tính năng đã thực hiện.
- 🐞 [Issue_lists.md](docs/issue_lists.md): Nhật ký các lỗi đã gặp và cách khắc phục.
- 💡 [Learned.md](docs/learned.md): Những kinh nghiệm và kiến thức mới đúc rút trong quá trình phát triển.
- ⏩ [Next_step.md](docs/next_step.md): Các tính năng dự kiến sẽ phát triển trong tương lai.

---

**Phát triển bởi**: Đặng Minh Tài 👨‍💻
**Trạng thái dự án**: Active Development 🚀
