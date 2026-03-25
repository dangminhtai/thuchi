# Danh sách lỗi (Issue Lists)

## ISS001: Lỗi biên dịch `Unresolved reference: launch` trong `AppDatabase.kt`
- **Nguyên nhân**: Sử dụng Coroutine mà quên `import` thư viện `launch`.
- **Action**: Bổ sung `import kotlinx.coroutines.launch` và `@OptIn(DelicateCoroutinesApi::class)`.
- **Giải pháp**: Luôn kiểm tra đầy đủ các `import` khi dùng Coroutines bên ngoài `ViewModel`.

## ISS002: Lỗi biên dịch `Expecting a top level declaration` trong `TransactionRepository.kt`
- **Nguyên nhân**: File bị mất khai báo `class` do lỗi ghi đè nội dung không cẩn thận.
- **Action**: Khôi phục khai báo `class TransactionRepository` và các tham số constructor.
- **Giải pháp**: Kiểm tra kỹ nội dung thay thế khi dùng công cụ `replace_file_content` để không làm mất cấu trúc file.
