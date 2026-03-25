# Danh sách lỗi (Issue Lists)

## ISS001: Lỗi biên dịch `Unresolved reference: launch` trong `AppDatabase.kt`
- **Nguyên nhân**: Sử dụng Coroutine mà quên `import` thư viện `launch`.
- **Action**: Bổ sung `import kotlinx.coroutines.launch` và `@OptIn(DelicateCoroutinesApi::class)`.
- **Giải pháp**: Luôn kiểm tra đầy đủ các `import` khi dùng Coroutines bên ngoài `ViewModel`.

## ISS002: Lỗi biên dịch `Expecting a top level declaration` trong `TransactionRepository.kt`
- **Nguyên nhân**: File bị mất khai báo `class` do lỗi ghi đè nội dung không cẩn thận.
- **Action**: Khôi phục khai báo `class TransactionRepository` và các tham số constructor.
- **Giải pháp**: Kiểm tra kỹ nội dung thay thế khi dùng công cụ `replace_file_content` để không làm mất cấu trúc file.

## ISS003: Lỗi tài nguyên `color/orange_700` không tìm thấy trong `dot_filled.xml`
- **Nguyên nhân**: Sử dụng sai tên biến màu sắc chưa có trong `colors.xml`.
- **Action**: Đổi `@color/orange_700` sang `@color/orange_main`.
- **Giải pháp**: Luôn kiểm tra file `colors.xml` trước khi gọi tên biến màu sắc mới.

## ISS004: Lỗi vòng lặp vô tận (Endless Loop) giữa MainActivity và LockActivity
- **Nguyên nhân**: MainActivity luôn kiểm tra có PIN hay không để chuyển hướng, mà không kiểm tra người dùng đã vừa mới nhập PIN xong hay chưa.
- **Action**: Thêm biến static `isAuthenticated` vào `SecurityUtils` để đánh dấu trạng thái đã xác thực trong phiên làm việc.
- **Giải pháp**: Với các màn hình chặn (Gatekeeper), cần có trạng thái lưu lại việc đã vượt qua cửa kiểm soát để tránh lặp lại logic chuyển hướng.
