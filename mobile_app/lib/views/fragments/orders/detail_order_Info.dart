import 'package:flutter/material.dart';

class DetailOrderInfoView extends StatelessWidget {

  final String barcode;

  DetailOrderInfoView({required this.barcode});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Thông tin sản phẩm')),
      body: Center(
        child: Text(
          'Mã đã quét: $barcode',
          style: const TextStyle(fontSize: 18),
        ),
      ),
    );
  }
}