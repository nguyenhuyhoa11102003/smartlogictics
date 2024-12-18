import 'package:flutter/material.dart';

class DetailsProfileView extends StatefulWidget {
  const DetailsProfileView({super.key});

  @override
  State<StatefulWidget> createState() => _DetailsProfileView();
}

class _DetailsProfileView extends State<DetailsProfileView> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _addressController = TextEditingController();
  final TextEditingController _dobController = TextEditingController();
  final TextEditingController _idCardController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sửa thông tin cá nhân', style: TextStyle(color: Colors.white),),
        centerTitle: true,
        backgroundColor: Colors.red[700],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 25),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              // Avatar
              Stack(
                alignment: Alignment.bottomRight,
                children: [
                  CircleAvatar(
                    radius: 60,
                    backgroundColor: Colors.grey[300],
                    backgroundImage: const NetworkImage(
                        'https://via.placeholder.com/150'), // Placeholder avatar
                  ),
                  IconButton(
                    icon: const Icon(Icons.camera_alt, color: Colors.grey),
                    onPressed: () {
                      // Handle change avatar logic
                    },
                  )
                ],
              ),
              const SizedBox(height: 26),

              // Text Fields
              _buildTextField('Họ và tên', _nameController, TextInputType.text),
              const SizedBox(height: 13),
              _buildTextField(
                  'Số điện thoại', _phoneController, TextInputType.phone),
              const SizedBox(height: 13),
              _buildTextField('Email', _emailController, TextInputType.emailAddress),
              const SizedBox(height: 13),
              _buildTextField('Địa chỉ', _addressController, TextInputType.text),
              const SizedBox(height: 13),
              _buildTextField('Sinh nhật', _dobController, TextInputType.datetime),
              const SizedBox(height: 13),
              _buildTextField('Căn cước công dân', _idCardController,
                  TextInputType.number),
              const SizedBox(height: 60),

              // Save Button
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () {
                    // Handle save action
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.red[700],
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    padding: const EdgeInsets.symmetric(vertical: 15),
                  ),
                  child: const Text(
                    'Lưu thay đổi',
                    style: TextStyle(fontSize: 16, color: Colors.white),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTextField(String labelText, TextEditingController controller,
      TextInputType keyboardType) {
    return TextField(
      controller: controller,
      keyboardType: keyboardType,
      decoration: InputDecoration(
        labelText: labelText,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
        ),
        contentPadding: const EdgeInsets.symmetric(horizontal: 15),
      ),
    );
  }
}
