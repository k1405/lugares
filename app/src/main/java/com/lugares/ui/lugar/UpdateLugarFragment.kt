package com.lugares.ui.lugar

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lugares.R
import com.lugares.databinding.FragmentUpdateLugarBinding
import com.lugares.databinding.FragmentLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel


class UpdateLugarFragment : Fragment() {
    private val args by navArgs<UpdateLugarFragmentArgs>()

    private lateinit var lugarViewModel: LugarViewModel

    private var _binding: FragmentUpdateLugarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         lugarViewModel =
            ViewModelProvider(this).get(LugarViewModel::class.java)

        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)

        binding.etNombre.setText(args.lugar.nombre.toString())
        binding.etCorreo.setText(args.lugar.correo)
        binding.etTelefono.setText(args.lugar.telefono)
        binding.etWeb.setText(args.lugar.web)
        binding.tvLongitud.text= args.lugar.longitud.toString()
        binding.tvLatitud.text= args.lugar.latitud.toString()
        binding.tvAltura.text= args.lugar.altura.toString()




        binding.btUpdate.setOnClickListener { UpdateLugar() }
        binding.btDelete.setOnClickListener { DeleteLugar() }
        binding.btEmail.setOnClickListener { escribirCorreo() }
        binding.btPhone.setOnClickListener { llamarLugar() }
        binding.btWhatsapp.setOnClickListener { enviarWhatsApp() }
        binding.btWeb.setOnClickListener { verWeb() }
        binding.btLocation.setOnClickListener { verEnMapa() }

        return binding.root
    }

    private fun escribirCorreo() {

        val valor= binding.etCorreo.text.toString()
        if(valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayListOf(valor))
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.msg_saludos)+ " "+ binding.etNombre.text)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_mensaje_correo))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(), getString(R.string.msg_data),Toast.LENGTH_LONG).show()

        }
    }

    private fun llamarLugar() {

    }

    private fun enviarWhatsApp() {
        val valor= binding.etTelefono.text.toString()
        if(valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_VIEW)
            val uri ="whatsapp://send?phone=506$valor&text=" +
            getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)

            startActivity(intent)
        }else{
            Toast.makeText(requireContext(), getString(R.string.msg_data),Toast.LENGTH_LONG).show()

        }
    }

    private fun verWeb() {

    }

    private fun verEnMapa() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun DeleteLugar(){
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_lugar)
        alerta.setMessage(getString(R.string.msg_pregunta_delete) + "${args.lugar.nombre}")
        alerta.setPositiveButton(getString(R.string.msg_si)) { _,_ ->
            lugarViewModel.deleteLugar(args.lugar) //
            Toast.makeText(requireContext(),getString(R.string.msg_pregunta_delete), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)

        }
        alerta.setNegativeButton(getString(R.string.msg_no)){_,_->}
        alerta.create().show()


    }

    private fun UpdateLugar() {
        val nombre = binding.etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val correo = binding.etCorreo.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val web = binding.etWeb.text.toString()
            val lugar = Lugar(args.lugar.id, nombre,correo,telefono,web,args.lugar.latitud,args.lugar.longitud,args.lugar.altura,args.lugar.rutaAudio,args.lugar.rutaImagen)
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_added),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()

        }


    }

}