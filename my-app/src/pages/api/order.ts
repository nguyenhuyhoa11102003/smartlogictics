import { NextApiRequest, NextApiResponse } from 'next';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
    if (req.method === 'POST') {
        const { formData } = req.body;
        console.log('Form data received:', formData);
        return res.status(200).json({ message: 'Order created successfully!', data: formData });
    }

    return res.status(405).json({ message: 'Method not allowed' });
}