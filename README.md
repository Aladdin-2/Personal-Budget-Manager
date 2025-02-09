# Büdcə İdarəetmə Sistemi (Budget Manager)

**Büdcə İdarəetmə Sistemi**, şəxsi maliyyəni idarə etmək üçün hazırlanmış **Spring Boot** əsaslı bir backend sistemidir. Bu sistem istifadəçilərə **gəlir və xərclərini izləməyə**, **bütün maliyyə əməliyyatlarını görməyə** və **ümumi büdcəni hesablamağa** imkan yaradır.

## 📌 Xüsusiyyətlər
✅ **Gəlir və xərcləri əlavə etmə və silmə**
✅ **Gəlir və xərcləri API vasitəsilə əldə etmə**
✅ **Ümumi büdcəni hesablama və göstərmə**
✅ **Məlumat bazası ilə əlaqə (H2 və ya MySQL dəstəyi)**
✅ **Spring Boot RESTful API ilə JSON formatında işləmə**

---

## 🚀 Quraşdırma və İstifadə
### 1️⃣ Layihəni Klonlayın
```sh
git clone https://github.com/istifadəçiadı  /budget-manager.git
cd budget-manager
```

### 2️⃣ Backend-i İşə Salın
Backend-in işləməsi üçün **Maven və JDK 17+** lazımdır. Backend-i işə salmaq üçün:
```sh
cd backend
mvn spring-boot:run
```
Bu əmrdən sonra **API (http://localhost:8080/aladdin.com/)** ünvanında işləyəcək.

---

## 🔗 API Əməliyyatları
Backend API aşağıdakı endpointləri təmin edir:

Ilkin olaraq qeyd etmək istəyirəm ki Büdcə yaratmadan xərc və gəlir yarada bilməzsiniz.
Əvvəlcə büdcə yaradılmalı və onun İD si ilə gəlir və xərc yaradılmalıdır.

### 📂 Xərc Endpoints (`aladdin.com/expense/`)
| Endpoint                  | Metod  | Açıqlama |
|---------------------------|--------|----------|
| `/expense/new`           | POST   | Yeni xərc əlavə edir |
| `/expense/get/{id}`      | GET    | Müəyyən xərci gətirir |
| `/expense/getAllExpense` | GET    | Bütün xərcləri gətirir |
| `/expense/update/{id}`   | PUT    | Xərci yeniləyir |
| `/expense/filterDate`    | GET    | Tarixə görə xərc axtarır |
| `/expense/category/{category}` | GET | Kateqoriyaya görə xərcləri gətirir |
| `/expense/delete/{id}`   | DELETE | Müəyyən xərci silir |
| `/expense/deleteAll`     | DELETE | Bütün xərcləri silir |

### 📂 Gəlir Endpoints (`aladdin.com/income/`)
| Endpoint                  | Metod  | Açıqlama |
|---------------------------|--------|----------|
| `/income/new`             | POST   | Yeni gəlir əlavə edir |
| `/income/get/{id}`        | GET    | Müəyyən gəliri gətirir |
| `/income/allIncome`       | GET    | Bütün gəlirləri gətirir |
| `/income/update/{id}`     | PUT    | Gəliri yeniləyir |
| `/income/filterIncome`    | GET    | Tarixə görə gəlir axtarır |
| `/income/source/{source}` | GET    | Gəlir mənbəyinə görə axtarır |
| `/income/delete/{id}`     | DELETE | Müəyyən gəliri silir |
| `/income/deleteAll`       | DELETE | Bütün gəlirləri silir |

### 📂 Büdcə Endpoints (`aladdin.com/budget/`)
| Endpoint                  | Metod  | Açıqlama |
|---------------------------|--------|----------|
| `/budget/new`             | POST   | Yeni büdcə əlavə edir |
| `/budget/getAll`          | GET    | Bütün büdcələri gətirir |
| `/budget/incomeGroup`     | GET    | Gəlir qruplarını qaytarır |
| `/budget/expenseGroup`    | GET    | Xərc qruplarını qaytarır |
| `/budget/findById/{id}`   | GET    | Müəyyən büdcəni gətirir |
| `/budget/delete/All`      | DELETE | Bütün büdcələri silir |

Bütün API sorğuları **JSON formatında** olmalıdır və aşağıdakı nümunəyə uyğun gəlməlidir:
```json
{
  "description": "Maaş",
  "amount": 5000
}
```
Qeyd! 
Bəzi sorğular məsələn `/expense/filterDate` @RequestParam şəklində yazılır


## 🗄 Məlumat Bazası
Layihə **H2 (daxili verilənlər bazası)** və **MySQL** ilə işləyə bilər. 
Əgər **H2 istifadə edirsinizsə**, heç bir əlavə konfiqurasiya tələb olunmur. 
Əgər **MySQL istifadə edəcəksinizsə**, `application.properties` faylında **verilənlər bazası bağlantı parametrlərini** dəyişməlisiniz:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/budget_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

