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

## ISS005: Lỗi biên dịch `Unresolved reference: R` trong `FragmentBaoCao.kt`
- **Nguyên nhân**: Thiếu `import com.example.thu_chi.R` sau khi cấu trúc lại code và thêm tính năng biểu đồ mới.
- **Action**: Bổ sung `import com.example.thu_chi.R`.
- **Giải pháp**: Đảm bảo các lớp tài nguyên luôn được import đầy đủ khi sử dụng ID thủ công thay vì thông qua ViewBinding.

## ISS006: Lỗi biên dịch `Unresolved reference: XAxis` trong `FragmentBaoCao.kt`
- **Nguyên nhân**: Thiếu `import com.github.mikephil.charting.components.XAxis` khi thiết lập cấu trúc cho biểu đồ cột mới.
- **Action**: Bổ sung `import com.github.mikephil.charting.components.XAxis`.
- **Giải pháp**: Kiểm tra các lớp thành phần của thư viện bên thứ 3 (như MPAndroidChart) khi sử dụng các hằng số hoặc ENUM của chúng.
