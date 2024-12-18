import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';

import '../orders/detail_order_Info.dart';

class DetailsScanQRView extends StatefulWidget {
  const DetailsScanQRView({super.key});

  @override
  State<DetailsScanQRView> createState() => _DetailsScanQRViewState();
}

class _DetailsScanQRViewState extends State<DetailsScanQRView> {
  MobileScannerController scannerController = MobileScannerController();
  bool isScanning = true; // Trạng thái quét

  @override
  void dispose() {
    scannerController.dispose();
    super.dispose();
  }

  void _onBarcodeDetected(BarcodeCapture capture) {
    if (!isScanning) return;

    final List<Barcode> barcodes = capture.barcodes;
    if (barcodes.isNotEmpty) {
      final Barcode barcode = barcodes.first; // Lấy mã đầu tiên
      if (barcode.rawValue != null) {
        setState(() {
          isScanning = false; // Tạm ngưng quét
        });
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) =>
                DetailOrderInfoView(barcode: barcode.rawValue!),
          ),
        ).then((_) {
          setState(() {
            isScanning = true; // Bật lại quét khi quay về
          });
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Barcode & QR Scanner'),
        centerTitle: true,
        backgroundColor: Colors.blue,
        actions: [
          IconButton(
            icon: Icon(Icons.flash_on),
            onPressed: () => scannerController.toggleTorch(),
          ),
        ],
      ),
      body: Stack(
        children: [
          // MobileScanner hiển thị camera và quét mã
          MobileScanner(
            controller: scannerController,
            onDetect: _onBarcodeDetected,
          ),

          // Giao diện khung quét
          Center(
            child: Stack(
              alignment: Alignment.center,
              children: [
                // Vùng mờ xung quanh
                ColorFiltered(
                  colorFilter: ColorFilter.mode(
                    Colors.black.withOpacity(0.5),
                    BlendMode.srcOut,
                  ),
                  child: Stack(
                    children: [
                      Container(
                        decoration: const BoxDecoration(
                          color: Colors.black,
                          backgroundBlendMode: BlendMode.dstOut,
                        ),
                      ),
                      Align(
                        alignment: Alignment.center,
                        child: Container(
                          height: 250,
                          width: 250,
                          decoration: BoxDecoration(
                            color: Colors.transparent,
                            borderRadius: BorderRadius.circular(12),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),

                // Viền khung quét
                Container(
                  height: 250,
                  width: 250,
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.white,
                      width: 2,
                    ),
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
              ],
            ),
          ),

          // Hướng dẫn quét
          Align(
            alignment: Alignment.bottomCenter,
            child: Padding(
              padding: const EdgeInsets.only(bottom: 20.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Text(
                    'Đưa mã vào vùng quét',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 16,
                    ),
                  ),
                  const SizedBox(height: 10),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.red,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    child: const Text('Hủy'),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
