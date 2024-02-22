package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Address;
import kraheja.commons.entity.AddressCK;

@Repository
public interface AddressRepository extends JpaRepository<Address, AddressCK>, CrudRepository<Address, AddressCK> {

	@Query("select e from Address e WHERE trim(e.addressCK.adrAdowner) = :partyCode AND trim(e.addressCK.adrAdsegment) = :adrAdsegment AND trim(e.addressCK.adrAdtype) = :addresstype AND trim(e.addressCK.adrAdser)= :adrAdser")
	Address findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(String partyCode, String adrAdsegment,
			String addresstype, String adrAdser);

	Address findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
			String partyCode, String adrAdsegment, String addresstype, String adrAdser);

	Address findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(String partycode,
			String adrAdsegment, String addresstype);

	Address findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegment(String partycode, String adrAdsegment);

	@Query(value = "SELECT adr_country FROM address WHERE trim(adr_adsegment) = 'PARTY' AND trim(adr_adowner) = :partyCode And trim(adr_adtype) IN ('PMT', 'LOC')", nativeQuery = true)
	String fetchCountryDetails(String partyCode);

	@Query(value = "SELECT adr_state FROM address WHERE trim(adr_adsegment) = 'BLDG' AND trim(adr_adowner) = :buildingCode AND trim(adr_adtype) IN ('PMT', 'LOC')", nativeQuery = true)
	String fetchStateDetails(String buildingCode);

	@Modifying
	@Query("delete Address e WHERE trim(e.addressCK.adrAdowner) = :partyCode AND trim(e.addressCK.adrAdsegment) = :adrAdsegment AND trim(e.addressCK.adrAdtype) = :addresstype AND trim(e.addressCK.adrAdser)= :adrAdser")
	public void deleteByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(String partyCode, String adrAdsegment,
			String addresstype, String adrAdser);
}
