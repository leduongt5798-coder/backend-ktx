# KTX Backend (Spring Boot, Java 21)

Backend chuẩn tầng (Controller → Service → Repository) để thay thế phần hardcode `localStorage/sessionStorage` trong FE HTML bạn đưa.

## Chạy theo kiểu gì?

### Cách 1 (khuyến nghị): chạy bằng Maven
```bat
cd ktx-backend
mvn spring-boot:run
```
Backend chạy ở: `http://localhost:8080`

### Cách 2: build jar rồi chạy
```bat
cd ktx-backend
mvn -DskipTests package
java -jar target/ktx-backend-0.0.1-SNAPSHOT.jar
```

## CORS (để FE gọi được)
Mặc định backend cho phép gọi từ:
- `http://localhost:5500`
- `http://127.0.0.1:5500`
- `http://localhost:3000`

Nếu bạn dùng port khác, sửa trong `WebConfig.java`.

## Database
Dùng H2 (in-memory) để chạy ngay, không cần cài MySQL.
- H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ktx`
- user: `sa` / password: (trống)

## API chính (map theo FE)

### Auth
- POST `/api/auth/login`  (demo)
  - username: `admin`
  - password: `123456`
  - trả token (demo), FE có thể lưu vào localStorage.

### Room
- GET `/api/rooms` (overview)
- GET `/api/rooms/{roomCode}/members`
- POST `/api/rooms/{roomCode}/members`
- DELETE `/api/rooms/{roomCode}/members/{studentCode}`

### Student
- GET `/api/students/{studentCode}`
- PUT `/api/students/{studentCode}`

### Bill
- GET `/api/bills/by-student/{studentCode}`

## Test
```bat
mvn test
```

## Quick test bằng curl
```bash
curl http://localhost:8080/api/rooms
curl http://localhost:8080/api/rooms/A1/members
curl http://localhost:8080/api/students/N23DCVT001
```
