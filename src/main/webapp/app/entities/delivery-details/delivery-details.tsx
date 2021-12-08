import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './delivery-details.reducer';
import { IDeliveryDetails } from 'app/shared/model/delivery-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DeliveryDetails = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const deliveryDetailsList = useAppSelector(state => state.deliveryDetails.entities);
  const loading = useAppSelector(state => state.deliveryDetails.loading);
  const totalItems = useAppSelector(state => state.deliveryDetails.totalItems);
  const links = useAppSelector(state => state.deliveryDetails.links);
  const entity = useAppSelector(state => state.deliveryDetails.entity);
  const updateSuccess = useAppSelector(state => state.deliveryDetails.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="delivery-details-heading" data-cy="DeliveryDetailsHeading">
        <Translate contentKey="mullyaApp.deliveryDetails.home.title">Delivery Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mullyaApp.deliveryDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mullyaApp.deliveryDetails.home.createLabel">Create new Delivery Details</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {deliveryDetailsList && deliveryDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deliveryType')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.deliveryType">Delivery Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pickupDate')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.pickupDate">Pickup Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deliveryDate')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.deliveryDate">Delivery Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('truckDetails')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.truckDetails">Truck Details</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deliveryAgentPhone')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.deliveryAgentPhone">Delivery Agent Phone</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pickupTime')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.pickupTime">Pickup Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deliveryTime')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.deliveryTime">Delivery Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('deliveryStatus')}>
                    <Translate contentKey="mullyaApp.deliveryDetails.deliveryStatus">Delivery Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.deliveryDetails.fromAddress">From Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.deliveryDetails.toAddress">To Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.deliveryDetails.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.deliveryDetails.cancellation">Cancellation</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {deliveryDetailsList.map((deliveryDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${deliveryDetails.id}`} color="link" size="sm">
                        {deliveryDetails.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`mullyaApp.DeliveryType.${deliveryDetails.deliveryType}`} />
                    </td>
                    <td>{deliveryDetails.pickupDate}</td>
                    <td>{deliveryDetails.deliveryDate}</td>
                    <td>{deliveryDetails.truckDetails}</td>
                    <td>{deliveryDetails.deliveryAgentPhone}</td>
                    <td>
                      {deliveryDetails.pickupTime ? (
                        <TextFormat type="date" value={deliveryDetails.pickupTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {deliveryDetails.deliveryTime ? (
                        <TextFormat type="date" value={deliveryDetails.deliveryTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {deliveryDetails.createdOn ? (
                        <TextFormat type="date" value={deliveryDetails.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{deliveryDetails.createdBy}</td>
                    <td>
                      {deliveryDetails.updatedOn ? (
                        <TextFormat type="date" value={deliveryDetails.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{deliveryDetails.updatedBy}</td>
                    <td>
                      <Translate contentKey={`mullyaApp.DeliveryStatus.${deliveryDetails.deliveryStatus}`} />
                    </td>
                    <td>
                      {deliveryDetails.fromAddress ? (
                        <Link to={`address/${deliveryDetails.fromAddress.id}`}>{deliveryDetails.fromAddress.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {deliveryDetails.toAddress ? (
                        <Link to={`address/${deliveryDetails.toAddress.id}`}>{deliveryDetails.toAddress.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{deliveryDetails.order ? <Link to={`order/${deliveryDetails.order.id}`}>{deliveryDetails.order.id}</Link> : ''}</td>
                    <td>
                      {deliveryDetails.cancellation ? (
                        <Link to={`cancellation-details/${deliveryDetails.cancellation.id}`}>{deliveryDetails.cancellation.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${deliveryDetails.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${deliveryDetails.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${deliveryDetails.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="mullyaApp.deliveryDetails.home.notFound">No Delivery Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DeliveryDetails;
