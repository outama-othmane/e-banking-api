package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultipleTransferRepository extends JpaRepository<MultipleTransfer, Long> {

    List<MultipleTransfer> findTop10ByFromAccountOrderByCreatedAtDesc(Account account);

    @Query(value = "SELECT DISTINCT "+
            "mt.id, "+
            "mt.created_at, "+
            "mt.updated_at, "+
            "mt.recipients_count, "+
            "(-1*mt.total_amount) as total_amount, "+
            "mt.from_account_id, "+
            "mt.transfer_date, "+
            "mt.reason "+
            "FROM "+
            "multiple_transfers as mt "+
            "WHERE "+
            "mt.from_account_id = ?1 "+
            "UNION "+
            "SELECT DISTINCT "+
            "mt.id, "+
            "mt.created_at, "+
            "mt.updated_at, "+
            "mt.recipients_count, "+
            "mtr.amount as total_amount, "+
            "mt.from_account_id, "+
            "mt.transfer_date, "+
            "mt.reason "+
            "FROM "+
            "multiple_transfers as mt "+
            "INNER JOIN multiple_transfer_recipients as mtr ON "+
            "mtr.multiple_transfer_id = mt.id "+
            "WHERE "+
            "mtr.account_number = ?2 "+
            "ORDER BY created_at DESC "+
            "LIMIT 10",
            nativeQuery = true)
    List<MultipleTransfer> latest10TransfersByFromAccountIdOrRecipientAccountNumber(Long fromAccountId, String recipientAccountNumber);
}
