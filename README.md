# Posts

JSONPlaceholder API'sinden post listesi çeken basit bir Android uygulaması. Listeden bir post'u kaydırarak silebilir, üstüne tıklayıp başlık/açıklamasını güncelleyebilirsin.

## Kullanılanlar

- Kotlin + Jetpack Compose
- MVVM, Hilt (DI)
- Retrofit, Room
- Coroutines + Flow
- Navigation Compose, Coil

## Nasıl çalışır

- Post listesi Room'a kaydediliyor, ekran hep Room'dan okuyor. Network'e sadece ilk açılışta gidiliyor.
- Silme ve güncelleme işlemleri local, yani API'ye tekrar istek atılmıyor.
- Silme işleminde birkaç saniyelik "geri al" seçeneği var.

## Çalıştırmak için

Android Studio'da açıp sync edip çalıştırman yeterli. Min SDK 24, ilk açılışta internet gerekiyor.